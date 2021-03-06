/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/rest/EntityResource.java.e.vm
 */
package uk.co.boots.columbus.cmdb.model.server.rest;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import uk.co.boots.columbus.cmdb.model.core.rest.support.CORSSupport;
import uk.co.boots.columbus.cmdb.model.environment.dto.EnvironmentDTO;
import uk.co.boots.columbus.cmdb.model.environment.dto.SubEnvironmentDTO;
import uk.co.boots.columbus.cmdb.model.hiera.dto.HieraDTO;
import uk.co.boots.columbus.cmdb.model.hiera.dto.HieraDTOService;
import uk.co.boots.columbus.cmdb.model.server.dto.ServerDTO;
import uk.co.boots.columbus.cmdb.model.server.dto.ServerDTOService;
import uk.co.boots.columbus.cmdb.model.server.repository.ServerRepository;

@RestController
@RequestMapping("/api/servers")
public class ServerResource {

    private final Logger log = LoggerFactory.getLogger(ServerResource.class);

    @Inject
    private ServerRepository serverRepository;
    @Inject
    private ServerDTOService serverDTOService;
    @Inject
    private HieraDTOService hieraDTOService;

    /**
     * Create a new Server.
     */
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerDTO> create(@RequestBody ServerDTO serverDTO) throws URISyntaxException {

        log.debug("Create ServerDTO : {}", serverDTO);

        if (serverDTO.isIdSet()) {
            return ResponseEntity.badRequest().header("Failure", "Cannot create Server with existing ID").body(null);
        }

        ServerDTO result = serverDTOService.save(serverDTO);

        return ResponseEntity.created(new URI("/api/servers/" + result.id)).body(result);
    }

    /**
    * Find by id Server.
    */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerDTO> findById(@PathVariable Long id) throws URISyntaxException {

        log.debug("Find by id Server : {}", id);

        return Optional.ofNullable(serverDTOService.findOne(id)).map(serverDTO -> new ResponseEntity<>(serverDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/configs/{serverName}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HieraDTO>> findConfigsByEnvName(@PathVariable String serverName) throws URISyntaxException {

        log.debug("Find configs for this Server: {}", serverName);
        List<HieraDTO> result = hieraDTOService.findHieraInfoForServer(serverName);
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);   
    }


    /**
     * Update Server.
     */
    @RequestMapping(value = "/", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerDTO> update(@RequestBody ServerDTO serverDTO) throws URISyntaxException {

        log.debug("Update ServerDTO : {}", serverDTO);

        if (!serverDTO.isIdSet()) {
            return create(serverDTO);
        }

        ServerDTO result = serverDTOService.save(serverDTO);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Find a Page of Server using query by example.
     */
    @RequestMapping(value = "/page", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<ServerDTO>> findAll(@RequestBody PageRequestByExample<ServerDTO> prbe) throws URISyntaxException {
        PageResponse<ServerDTO> pageResponse = serverDTOService.findAll(prbe);
        return new ResponseEntity<>(pageResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
    * Auto complete support.
    */
    @RequestMapping(value = "/complete", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServerDTO>> complete(@RequestBody AutoCompleteQuery acq) throws URISyntaxException {

        List<ServerDTO> results = serverDTOService.complete(acq.query, acq.maxResults);

        return new ResponseEntity<>(results, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/available_environments/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnvironmentDTO>> findAvailableEnvironments(@PathVariable Long id, HttpServletRequest request, 
            HttpServletResponse response) throws URISyntaxException {

        log.debug("Find unassigned environments for id Server : {}", id);
        List<EnvironmentDTO> results = serverDTOService.getEnvironmentsNotAssignedToServer(id);

        return new ResponseEntity<>(results, CORSSupport.createCORSHeaders(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/notin", method = POST, produces = APPLICATION_JSON_VALUE, consumes= APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServerDTO>> findServersNotIn(@RequestBody List<ServerDTO> servers, HttpServletRequest request, 
            HttpServletResponse response) throws URISyntaxException {

        log.debug("Find servers not in given list : {}", servers);
        List<ServerDTO> results = serverDTOService.getServersNotInList(servers);

        return new ResponseEntity<>(results, CORSSupport.createCORSHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/notinpageable", method = POST, produces = APPLICATION_JSON_VALUE, consumes= APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<ServerDTO>> findAndPageAvailableEnvironments(@RequestBody PageRequestByExample<SubEnvironmentDTO> prbe) throws URISyntaxException {

        log.debug("Find servers not in SubEnvironment : {}", prbe);
        PageResponse<ServerDTO> results = serverDTOService.getServersNotInListForSubEnvironment(prbe);

        return new ResponseEntity<>(results, CORSSupport.createCORSHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServerDTO>> findAll(HttpServletRequest request, 
            HttpServletResponse response) throws URISyntaxException {

        log.debug("Find All Servers");
        List<ServerDTO> results = serverDTOService.getAll();

        return new ResponseEntity<>(results, CORSSupport.createCORSHeaders(), HttpStatus.OK);
    }

    /**
     * Delete by id ${}entity.model.type}.
     */
    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) throws URISyntaxException {

        log.debug("Delete by id Server : {}", id);

        try {
            serverRepository.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception x) {
            // todo: dig exception, most likely org.hibernate.exception.ConstraintViolationException
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}