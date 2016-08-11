package com.callistech.analytics.frontend.domains.uc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.callistech.analytics.frontend.domains.uc.entities.User;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);

	void deleteByDomainId(String domainId);

	List<User> findByDomainId(String domainId);

	User findByUsername(String username);

}
// end::code[]