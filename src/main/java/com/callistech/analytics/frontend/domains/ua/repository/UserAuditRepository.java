package com.callistech.analytics.frontend.domains.ua.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.callistech.analytics.frontend.domains.ua.entities.UserAudit;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RepositoryRestResource
public interface UserAuditRepository extends MongoRepository<UserAudit, String> {

	@Query("{ 'action' : { $regex: 'DND' } }")
	List<UserAudit> findAllDND();

	@Query("{ 'action' : { $regex: 'DND' } }")
	Page<UserAudit> findAllDND(Pageable pageable);

	@Query(count = true, value = "{ 'action' : { $regex: 'DND' } }")
	long countAllDND();
}
// end::code[]