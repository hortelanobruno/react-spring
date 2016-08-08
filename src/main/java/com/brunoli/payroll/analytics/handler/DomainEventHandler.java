package com.brunoli.payroll.analytics.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.brunoli.payroll.analytics.entities.Domain;
import com.brunoli.payroll.analytics.entities.User;
import com.brunoli.payroll.analytics.repository.DomainRepository;
import com.brunoli.payroll.analytics.repository.UserRepository;
import com.brunoli.payroll.analytics.util.DomainModelException;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(Domain.class)
public class DomainEventHandler {

	private final DomainRepository domainRepository;
	private final UserRepository userRepository;

	@Autowired
	public DomainEventHandler(DomainRepository domainRepository, UserRepository userRepository) {
		this.domainRepository = domainRepository;
		this.userRepository = userRepository;
	}

	@HandleBeforeCreate
	public void beforeCreateDomain(Domain domain) throws DomainModelException {
		System.out.println("before create: " + domain);
//		if (domain.getName() == null) {
//			throw new WildeException("Domain name is invalid.");
//		}
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// if (auth == null) {
		// throw new WildeException("No manager found for user on applyUserInformationUsingSecurityContext.");
		// }
		// String name = auth.getName();
		// User user = this.userRepository.findByEmail(name);
		// if (user == null) {
		// throw new WildeException("No manager found for user on applyUserInformationUsingSecurityContext.");
		// }
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