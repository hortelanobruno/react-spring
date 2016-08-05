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

import com.brunoli.payroll.analytics.entities.Group;
import com.brunoli.payroll.analytics.repository.GroupRepository;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(Group.class)
public class GroupEventHandler {

	private final GroupRepository groupRepository;

	@Autowired
	public GroupEventHandler(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	@HandleBeforeCreate
	public void beforeCreateGroup(Group group) {
		System.out.println("before create: " + group);
	}

	@HandleAfterCreate
	public void afterCreateGroup(Group group) {
		System.out.println("after create: " + group);
	}

	@HandleBeforeSave
	public void beforeSaveGroup(Group group) {
		System.out.println("before save: " + group);
	}

	@HandleAfterSave
	public void afterSaveGroup(Group group) {
		System.out.println("after save: " + group);
	}

	@HandleBeforeDelete
	public void beforeDeleteGroup(Group group) {
		System.out.println("before delete: " + group);
	}

	@HandleAfterDelete
	public void afterDeleteGroup(Group group) {
		System.out.println("after delete: " + group);
	}
}
// end::code[]