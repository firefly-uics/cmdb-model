/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/repository/EntityRepository.java.e.vm
 */
package uk.co.boots.columbus.cmdb.model.environment.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import uk.co.boots.columbus.cmdb.model.environment.domain.Environment;
import uk.co.boots.columbus.cmdb.model.environment.domain.EnvironmentType;
import uk.co.boots.columbus.cmdb.model.environment.domain.Environment_;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {

	/**
	 * Return the persistent instance of {@link Environment} with the given
	 * unique property value name, or null if there is no such persistent
	 * instance.
	 *
	 * @param name the unique value
	 * @return the corresponding {@link Environment} persistent instance or null
	 */
	Environment getByName(String name);

	default List<Environment> complete(String query, int maxResults) {
		Environment probe = new Environment();
		probe.setName(query);

		ExampleMatcher matcher = ExampleMatcher.matching() //
				.withMatcher(Environment_.name.getName(), match -> match.ignoreCase().contains());

		Page<Environment> page = findAll(Example.of(probe, matcher), new PageRequest(0, maxResults));
		return page.getContent();
	}
}