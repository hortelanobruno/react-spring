package com.callistech.analytics.frontend.domains.settings.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.callistech.analytics.frontend.domains.settings.entities.Setting;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RepositoryRestResource
public interface SettingRepository extends MongoRepository<Setting, String> {

	public Setting findByName(String name);
}
// end::code[]