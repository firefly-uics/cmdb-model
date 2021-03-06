/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/rest/EntityResource.java.e.vm
 */
package uk.co.boots.columbus.cmdb.model.release.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import uk.co.boots.columbus.cmdb.model.core.dto.support.PageRequestByExample;
import uk.co.boots.columbus.cmdb.model.core.dto.support.PageResponse;
import uk.co.boots.columbus.cmdb.model.core.rest.support.AutoCompleteQuery;
import uk.co.boots.columbus.cmdb.model.core.rest.support.CsvResponse;
import uk.co.boots.columbus.cmdb.model.hiera.dto.HieraDTO;
import uk.co.boots.columbus.cmdb.model.hiera.dto.HieraDTOService;
import uk.co.boots.columbus.cmdb.model.release.dto.ReleaseDTO;
import uk.co.boots.columbus.cmdb.model.release.dto.ReleaseDTOService;
import uk.co.boots.columbus.cmdb.model.release.repository.ReleaseRepository;

@RestController
@RequestMapping("/api/releases")
public class ReleaseResource {

    private final Logger log = LoggerFactory.getLogger(ReleaseResource.class);

    @Inject
    private ReleaseRepository releaseRepository;
    @Inject
    private ReleaseDTOService releaseDTOService;
    @Inject
    private HieraDTOService hieraDTOService;

    /**
     * Create a new Release.
     */
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleaseDTO> create(@RequestBody ReleaseDTO releaseDTO) throws URISyntaxException {

        log.debug("Create ReleaseDTO : {}", releaseDTO);

        if (releaseDTO.isIdSet()) {
            return ResponseEntity.badRequest().header("Failure", "Cannot create Release with existing ID").body(null);
        }

        ReleaseDTO result = releaseDTOService.save(releaseDTO);

        return ResponseEntity.created(new URI("/api/releases/" + result.id)).body(result);
    }

    /**
    * Find by id Release.
    */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleaseDTO> findById(@PathVariable Long id) throws URISyntaxException {

        log.debug("Find by id Release : {}", id);

        return Optional.ofNullable(releaseDTOService.findOne(id)).map(releaseDTO -> new ResponseEntity<>(releaseDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/configs/{relName:.*}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HieraDTO>> findConfigsByReleaseName(@PathVariable String relName) throws URISyntaxException {

        log.debug("Find configs for this Release : {}", relName);

        List<HieraDTO> result = hieraDTOService.findHieraInfoForRelease(relName);
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);   
    }
    
    @RequestMapping(value = "/configdownloadall", method = GET, produces = "text/csv")
    @ResponseBody // indicate to use a compatible HttpMessageConverter
    public CsvResponse downloadAllConfigsForRelease(@PathVariable String relName) throws IOException {
        return null;
    	//return new CsvResponse(hieraDTOService.findHieraForAllReleases(), "HieraData_Release_Complete" + relName + ".csv");
    }


    @RequestMapping(value = "/configdownload/{relName:.*}", method = GET, produces = "text/csv")
    @ResponseBody // indicate to use a compatible HttpMessageConverter
    public CsvResponse downloadConfigsByReleaseName(@PathVariable String relName) throws IOException {
        return new CsvResponse(hieraDTOService.findHieraInfoForRelease(relName), "HieraData_Release_" + relName + ".csv");
    }
    
    /**
     * Update Release.
     */
    @RequestMapping(value = "/", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleaseDTO> update(@RequestBody ReleaseDTO releaseDTO) throws URISyntaxException {

        log.debug("Update ReleaseDTO : {}", releaseDTO);

        if (!releaseDTO.isIdSet()) {
            return create(releaseDTO);
        }

        ReleaseDTO result = releaseDTOService.save(releaseDTO);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Find a Page of Release using query by example.
     */
    @RequestMapping(value = "/page", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<ReleaseDTO>> findAll(@RequestBody PageRequestByExample<ReleaseDTO> prbe) throws URISyntaxException {
        PageResponse<ReleaseDTO> pageResponse = releaseDTOService.findAll(prbe);
        return new ResponseEntity<>(pageResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
    * Auto complete support.
    */
    @RequestMapping(value = "/complete", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReleaseDTO>> complete(@RequestBody AutoCompleteQuery acq) throws URISyntaxException {

        List<ReleaseDTO> results = releaseDTOService.complete(acq.query, acq.maxResults);

        return new ResponseEntity<>(results, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Delete by id ${}entity.model.type}.
     */
    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) throws URISyntaxException {

        log.debug("Delete by id Release : {}", id);

        try {
            releaseRepository.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception x) {
            // todo: dig exception, most likely org.hibernate.exception.ConstraintViolationException
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}