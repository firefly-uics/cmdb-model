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

import uk.co.boots.columbus.cmdb.model.domain.Globalconfig;
import uk.co.boots.columbus.cmdb.model.domain.Server;
import uk.co.boots.columbus.cmdb.model.domain.Server_;

public interface ServerRepository extends JpaRepository<Server, Long> {

    /**
     * Return the persistent instance of {@link Server} with the given unique property value name,
     * or null if there is no such persistent instance.
     *
     * @param name the unique value
     * @return the corresponding {@link Server} persistent instance or null
     */
    Server getByName(String name);

    default List<Server> complete(String query, int maxResults) {
        Server probe = new Server();
        probe.setName(query);

        ExampleMatcher matcher = ExampleMatcher.matching() //
                .withMatcher(Server_.name.getName(), match -> match.ignoreCase().startsWith());

        Page<Server> page = findAll(Example.of(probe, matcher), new PageRequest(0, maxResults));
        return page.getContent();
    }
}