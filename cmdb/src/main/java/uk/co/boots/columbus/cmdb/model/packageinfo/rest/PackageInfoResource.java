/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/rest/EntityResource.java.e.vm
 */
package uk.co.boots.columbus.cmdb.model.packageinfo.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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
import org.springframework.web.bind.annotation.RestController;

import uk.co.boots.columbus.cmdb.model.core.dto.support.PageRequestByExample;
import uk.co.boots.columbus.cmdb.model.core.dto.support.PageResponse;
import uk.co.boots.columbus.cmdb.model.core.rest.support.AutoCompleteQuery;
import uk.co.boots.columbus.cmdb.model.packageinfo.dto.PackageInfoDTO;
import uk.co.boots.columbus.cmdb.model.packageinfo.dto.PackageInfoDTOService;
import uk.co.boots.columbus.cmdb.model.packageinfo.repository.PackageInfoRepository;

@RestController
@RequestMapping("/api/packageInfos")
public class PackageInfoResource {

    private final Logger log = LoggerFactory.getLogger(PackageInfoResource.class);

    @Inject
    private PackageInfoRepository packageInfoRepository;
    @Inject
    private PackageInfoDTOService packageInfoDTOService;

    /**
     * Create a new PackageInfo.
     */
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PackageInfoDTO> create(@RequestBody PackageInfoDTO packageInfoDTO) throws URISyntaxException {

        log.debug("Create PackageInfoDTO : {}", packageInfoDTO);

        if (packageInfoDTO.isIdSet()) {
            return ResponseEntity.badRequest().header("Failure", "Cannot create PackageInfo with existing ID").body(null);
        }

        PackageInfoDTO result = packageInfoDTOService.save(packageInfoDTO);

        return ResponseEntity.created(new URI("/api/packageInfos/" + result.id)).body(result);
    }

    /**
    * Find by id PackageInfo.
    */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PackageInfoDTO> findById(@PathVariable Long id) throws URISyntaxException {

        log.debug("Find by id PackageInfo : {}", id);

        return Optional.ofNullable(packageInfoDTOService.findOne(id)).map(packageInfoDTO -> new ResponseEntity<>(packageInfoDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update PackageInfo.
     */
    @RequestMapping(value = "/", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PackageInfoDTO> update(@RequestBody PackageInfoDTO packageInfoDTO) throws URISyntaxException {

        log.debug("Update PackageInfoDTO : {}", packageInfoDTO);

        if (!packageInfoDTO.isIdSet()) {
            return create(packageInfoDTO);
        }

        PackageInfoDTO result = packageInfoDTOService.save(packageInfoDTO);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Find a Page of PackageInfo using query by example.
     */
    @RequestMapping(value = "/page", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<PackageInfoDTO>> findAll(@RequestBody PageRequestByExample<PackageInfoDTO> prbe) throws URISyntaxException {
        PageResponse<PackageInfoDTO> pageResponse = packageInfoDTOService.findAll(prbe);
        return new ResponseEntity<>(pageResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
    * Auto complete support.
    */
    @RequestMapping(value = "/complete", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PackageInfoDTO>> complete(@RequestBody AutoCompleteQuery acq) throws URISyntaxException {

        List<PackageInfoDTO> results = packageInfoDTOService.complete(acq.query, acq.maxResults);

        return new ResponseEntity<>(results, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Delete by id ${}entity.model.type}.
     */
    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) throws URISyntaxException {

        log.debug("Delete by id PackageInfo : {}", id);

        try {
            packageInfoRepository.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception x) {
            // todo: dig exception, most likely org.hibernate.exception.ConstraintViolationException
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}