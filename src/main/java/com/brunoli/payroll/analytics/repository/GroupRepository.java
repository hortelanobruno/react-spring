package com.brunoli.payroll.analytics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brunoli.payroll.analytics.entities.Group;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface GroupRepository extends MongoRepository<Group, String> {

}
// end::code[]