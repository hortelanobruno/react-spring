package com.brunoli.payroll.analytics.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.brunoli.payroll.analytics.entities.Domain;
import com.brunoli.payroll.analytics.repository.DomainRepository;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(Domain.class)
public class DomainEventHandler {

	private final DomainRepository domainRepository;

	@Autowired
	public DomainEventHandler(DomainRepository domainRepository) {
		this.domainRepository = domainRepository;
	}

	@HandleBeforeCreate
	public void beforeCreateDomain(Domain domain) {
		System.out.println("before create: " + domain);
	}

	@HandleAfterCreate
	public void afterCreateDomain(Domain domain) {
		System.out.println("after create: " + domain);
	}

	@HandleBeforeSave
	public void beforeSaveDomain(Domain domain) {
		System.out.println("before save: " + domain);
	}

	@HandleAfterSave
	public void afterSaveDomain(Domain domain) {
		System.out.println("after save: " + domain);
	}

	@HandleBeforeDelete
	public void beforeDeleteDomain(Domain domain) {
		System.out.println("before delete: " + domain);
	}

	@HandleAfterDelete
	public void afterDeleteDomain(Domain domain) {
		System.out.println("after delete: " + domain);
	}
}
// end::code[]