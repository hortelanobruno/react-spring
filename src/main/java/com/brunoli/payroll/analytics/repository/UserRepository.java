package com.brunoli.payroll.analytics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brunoli.payroll.analytics.entities.User;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);

}
// end::code[]