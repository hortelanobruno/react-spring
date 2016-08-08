package com.brunoli.payroll.analytics.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.brunoli.payroll.analytics.entities.Domain;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RepositoryRestResource
public interface DomainRepository extends MongoRepository<Domain, String> {

	@RestResource(path = "byDesc")
	public List findByDescription(@Param("description") String description);
}
// end::code[]