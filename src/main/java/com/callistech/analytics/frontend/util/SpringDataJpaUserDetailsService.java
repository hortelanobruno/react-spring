package com.callistech.analytics.frontend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.callistech.analytics.frontend.domains.uc.repository.UserRepository;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public SpringDataJpaUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		com.callistech.analytics.frontend.domains.uc.entities.User manager = this.userRepository.findByUsername(name);
		return new User(manager.getUsername(), manager.getPassword(), AuthorityUtils.createAuthorityList("MANAGER"));
	}

}
// end::code[]