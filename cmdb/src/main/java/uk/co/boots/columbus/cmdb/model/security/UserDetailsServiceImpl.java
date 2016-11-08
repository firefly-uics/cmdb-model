/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/security/UserDetailsServiceImpl.java.p.vm
 */
package uk.co.boots.columbus.cmdb.model.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.boots.columbus.cmdb.model.domain.Role;
import uk.co.boots.columbus.cmdb.model.domain.User;
import uk.co.boots.columbus.cmdb.model.repository.RoleRepository;
import uk.co.boots.columbus.cmdb.model.repository.UserRepository;

/**
 * An implementation of Spring Security's {@link UserDetailsService}.
 * 
 * @see http://static.springsource.org/spring-security/site/reference.html
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Inject
    private UserRepository userRepo;
    @Inject
    private RoleRepository roleRepo;
    
    /**
     * Retrieve an account depending on its login this method is not case sensitive.
     *
     * @param username the user's username
     * @return a Spring Security userdetails object that matches the username
     * @throws UsernameNotFoundException when the user could not be found
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

    	if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("Empty username");
        }
        log.debug("Security verification for user '{}'", username);

        User user = userRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not recognized");
        }
        log.debug("Security verification for user '{}'", username);

             
        UserDetails ud = new UserWithId(username, user.getPassword(), user.getEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked, toGrantedAuthorities(user.getRoles()), null); 
        return ud;        
    }

    private Collection<GrantedAuthority> toGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            result.add(new SimpleGrantedAuthority(role.getName()));
        }
        return result;
    }
}