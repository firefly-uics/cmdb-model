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

import uk.co.boots.columbus.cmdb.model.domain.ComponentConfig;
import uk.co.boots.columbus.cmdb.model.domain.ComponentConfig_;

public interface ComponentConfigRepository extends JpaRepository<ComponentConfig, Long> {

	List<ComponentConfig> findBySolutionComponent_id(Long Id);
	List<ComponentConfig> findBySolutionComponent_PackageInfo_Release_name(String name);
	default List<ComponentConfig> complete(String query, int maxResults) {
        ComponentConfig probe = new ComponentConfig();
        probe.setParameter(query);

        ExampleMatcher matcher = ExampleMatcher.matching() //
                .withMatcher(ComponentConfig_.parameter.getName(), match -> match.ignoreCase().startsWith());

        Page<ComponentConfig> page = findAll(Example.of(probe, matcher), new PageRequest(0, maxResults));
        return page.getContent();
    }
}