package uk.co.boots.columbus.cmdb.model.component.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;
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

import uk.co.boots.columbus.cmdb.model.component.dto.SolutionComponentDTO;
import uk.co.boots.columbus.cmdb.model.component.dto.SolutionComponentDTOService;
import uk.co.boots.columbus.cmdb.model.component.repository.SolutionComponentRepository;
import uk.co.boots.columbus.cmdb.model.core.dto.support.PageRequestByExample;
import uk.co.boots.columbus.cmdb.model.core.dto.support.PageResponse;
import uk.co.boots.columbus.cmdb.model.core.rest.support.AutoCompleteQuery;
import uk.co.boots.columbus.cmdb.model.core.rest.support.CsvResponse;
import uk.co.boots.columbus.cmdb.model.hiera.dto.HieraDTO;
import uk.co.boots.columbus.cmdb.model.hiera.dto.HieraDTOService;

@RestController
@RequestMapping("/api/solutionComponents")
public class SolutionComponentResource {

	private final Logger log = LoggerFactory.getLogger(SolutionComponentResource.class);

	@Inject
	private SolutionComponentRepository solutionComponentRepository;
	@Inject
	private SolutionComponentDTOService solutionComponentDTOService;
	@Inject
	private HieraDTOService hieraDTOService;

	/**
	 * Create a new SolutionComponent.
	 */
	@RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<SolutionComponentDTO> create(@RequestBody SolutionComponentDTO solutionComponentDTO)
			throws URISyntaxException {

		log.debug("Create SolutionComponentDTO : {}", solutionComponentDTO);

		if (solutionComponentDTO.isIdSet()) {
			return ResponseEntity.badRequest().header("Failure", "Cannot create SolutionComponent with existing ID")
					.body(null);
		}

		SolutionComponentDTO result = solutionComponentDTOService.save(solutionComponentDTO);

		return ResponseEntity.created(new URI("/api/solutionComponents/" + result.id)).body(result);
	}

	/**
	 * Find by id SolutionComponent.
	 */
	@RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<SolutionComponentDTO> findById(@PathVariable Long id) throws URISyntaxException {

		log.debug("Find by id SolutionComponent : {}", id);

		return Optional.ofNullable(solutionComponentDTOService.findOne(id))
				.map(solutionComponentDTO -> new ResponseEntity<>(solutionComponentDTO, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping(value = "/configs/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HieraDTO>> findConfigsByReleaseName(@PathVariable Long id) throws URISyntaxException {

		log.debug("Find configs for Component : {}", id);
		List<HieraDTO> result = hieraDTOService.findHieraInfoForSolutionComponent(id);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/configdownload/{id}", method = GET, produces = "text/csv")
	@ResponseBody // indicate to use a compatible HttpMessageConverter
	public CsvResponse downloadConfigsByReleaseName(@PathVariable Long id) throws IOException {
		List<HieraDTO> result = hieraDTOService.findHieraInfoForSolutionComponent(id);
		SolutionComponentDTO scDTO = solutionComponentDTOService.findOne(id);
		return new CsvResponse(result, "HieraData_Component_" + scDTO.name + ".csv");
	}

	/**
	 * Update SolutionComponent.
	 */
	@RequestMapping(value = "/", method = PUT, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<SolutionComponentDTO> update(@RequestBody SolutionComponentDTO solutionComponentDTO)
			throws URISyntaxException {

		log.debug("Update SolutionComponentDTO : {}", solutionComponentDTO);

		if (!solutionComponentDTO.isIdSet()) {
			return create(solutionComponentDTO);
		}

		SolutionComponentDTO result = solutionComponentDTOService.save(solutionComponentDTO);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * Find a Page of SolutionComponent using query by example.
	 */
	@RequestMapping(value = "/page", method = POST, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PageResponse<SolutionComponentDTO>> findAll(
			@RequestBody PageRequestByExample<SolutionComponentDTO> prbe) throws URISyntaxException {
		PageResponse<SolutionComponentDTO> pageResponse = solutionComponentDTOService.findAll(prbe);
		return new ResponseEntity<>(pageResponse, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Auto complete support.
	 */
	@RequestMapping(value = "/complete", method = POST, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SolutionComponentDTO>> complete(@RequestBody AutoCompleteQuery acq)
			throws URISyntaxException {

		List<SolutionComponentDTO> results = solutionComponentDTOService.complete(acq.query, acq.maxResults);

		return new ResponseEntity<>(results, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Delete by id ${}entity.model.type}.
	 */
	@RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Long id) throws URISyntaxException {

		log.debug("Delete by id SolutionComponent : {}", id);

		try {
			solutionComponentRepository.delete(id);
			return ResponseEntity.ok().build();
		} catch (Exception x) {
			// todo: dig exception, most likely
			// org.hibernate.exception.ConstraintViolationException
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}