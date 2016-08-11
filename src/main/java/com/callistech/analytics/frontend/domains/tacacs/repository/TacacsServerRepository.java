package com.callistech.analytics.frontend.domains.tacacs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.callistech.analytics.frontend.domains.tacacs.entity.TacacsServer;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RepositoryRestResource
public interface TacacsServerRepository extends MongoRepository<TacacsServer, String> {

}
// end::code[]