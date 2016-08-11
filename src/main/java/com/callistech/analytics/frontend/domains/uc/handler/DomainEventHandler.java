package com.callistech.analytics.frontend.domains.uc.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.callistech.analytics.frontend.domains.uc.entities.Domain;
import com.callistech.analytics.frontend.domains.uc.entities.Group;
import com.callistech.analytics.frontend.domains.uc.entities.User;
import com.callistech.analytics.frontend.domains.uc.repository.DomainRepository;
import com.callistech.analytics.frontend.domains.uc.repository.GroupRepository;
import com.callistech.analytics.frontend.domains.uc.repository.UserRepository;
import com.callistech.analytics.frontend.domains.uc.util.DomainModelException;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(Domain.class)
public class DomainEventHandler {

	private final DomainRepository domainRepository;
	private final UserRepository userRepository;
	private final GroupRepository groupRepository;

	@Autowired
	public DomainEventHandler(DomainRepository domainRepository, UserRepository userRepository, GroupRepository groupRepository) {
		this.domainRepository = domainRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
	}

	@HandleBeforeCreate
	public void beforeCreateDomain(Domain domain) throws DomainModelException {
		System.out.println("before create: " + domain);
		// if (domain.getName() == null) {
		// throw new WildeException("Domain name is invalid.");
		// }
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
		// Creo usuario y group default
		Group group = generateDefaultGroup(domain);
		groupRepository.save(group);
		User user = generateDefaultUser(domain, group);
		userRepository.save(user);
	}

	private Group generateDefaultGroup(Domain domain) {
		Group group = new Group();
		group.setName("Administrator " + domain.getName());
		group.setDescription("Group for the administrators of " + domain.getName());
		group.setDomainId(domain.getId());
		group.setIsDefault(true);
		return group;
	}

	private User generateDefaultUser(Domain domain, Group group) {
		User user = new User();
		user.setEmail("admin@" + domain.getName() + ".com");
		user.setDomainId(domain.getId());
		user.setPassword("admin");
		user.setGroupsIds(new ArrayList<>());
		user.setIsDefault(true);
		user.getGroupsIds().add(group.getId());
		return user;
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
		// Borro los usuarios y groups asociados a ese domain
		groupRepository.deleteByDomainId(domain.getId());
		userRepository.deleteByDomainId(domain.getId());
	}

	@HandleAfterDelete
	public void afterDeleteDomain(Domain domain) {
		System.out.println("after delete: " + domain);
	}
}
// end::code[]