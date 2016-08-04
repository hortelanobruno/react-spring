package com.brunoli.payroll;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RepositoryRestResource(exported = false)
public interface ManagerRepository extends MongoRepository<Manager, String> {

	Manager save(Manager manager);

	Manager findByName(String name);

	void deleteAll();

}
// end::code[]