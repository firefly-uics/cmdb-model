/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/repository/EntityRepository.java.e.vm
 */
package uk.co.boots.columbus.cmdb.model.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.*;

import uk.co.boots.columbus.cmdb.model.domain.SolutionComponent;
import uk.co.boots.columbus.cmdb.model.domain.SolutionComponent_;

public interface SolutionComponentRepository extends JpaRepository<SolutionComponent, Long> {

	default List<SolutionComponent> complete(String query, int maxResults) {
        SolutionComponent probe = new SolutionComponent();
        probe.setName(query);

        ExampleMatcher matcher = ExampleMatcher.matching() //
                .withMatcher(SolutionComponent_.name.getName(), match -> match.ignoreCase().startsWith());

        Page<SolutionComponent> page = findAll(Example.of(probe, matcher), new PageRequest(0, maxResults));
        return page.getContent();
    }
}