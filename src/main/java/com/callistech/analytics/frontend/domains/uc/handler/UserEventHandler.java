package com.callistech.analytics.frontend.domains.uc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.callistech.analytics.frontend.domains.uc.entities.User;
import com.callistech.analytics.frontend.domains.uc.repository.UserRepository;
import com.callistech.analytics.frontend.domains.uc.util.DomainModelException;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

	private final UserRepository userRepository;

	@Autowired
	public UserEventHandler(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@HandleBeforeCreate
	public void beforeCreateUser(User user) {
		System.out.println("before create: " + user);
	}

	@HandleAfterCreate
	public void afterCreateUser(User user) {
		System.out.println("after create: " + user);
	}

	@HandleBeforeSave
	public void beforeSaveUser(User user) {
		System.out.println("before save: " + user);
	}

	@HandleAfterSave
	public void afterSaveUser(User user) {
		System.out.println("after save: " + user);
	}

	@HandleBeforeDelete
	public void beforeDeleteUser(User user) {
		System.out.println("before delete: " + user);
		if (user.getIsDefault()) {
			throw new DomainModelException("User default cant be deleted.");
		}
	}

	@HandleAfterDelete
	public void afterDeleteUser(User user) {
		System.out.println("after delete: " + user);
	}
}
// end::code[]