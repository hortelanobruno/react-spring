package com.callistech.analytics.frontend.domains.tacacs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.callistech.analytics.frontend.domains.tacacs.entity.TacacsServerAuthType;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RepositoryRestResource
public interface TacacsServerAuthRepository extends MongoRepository<TacacsServerAuthType, String> {

}
// end::code[]