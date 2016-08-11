package com.callistech.analytics.frontend.domains.uc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.callistech.analytics.frontend.domains.uc.entities.Group;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface GroupRepository extends MongoRepository<Group, String> {

	void deleteByDomainId(String domainId);

	Group findByName(String name);

}
// end::code[]