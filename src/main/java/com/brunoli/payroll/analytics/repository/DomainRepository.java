package com.brunoli.payroll.analytics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brunoli.payroll.analytics.entities.Domain;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface DomainRepository extends MongoRepository<Domain, String> {

}
// end::code[]