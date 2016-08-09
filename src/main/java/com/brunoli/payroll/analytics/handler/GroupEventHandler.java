package com.brunoli.payroll.analytics.handler;

import java.util.List;

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
import com.brunoli.payroll.analytics.entities.User;
import com.brunoli.payroll.analytics.repository.GroupRepository;
import com.brunoli.payroll.analytics.repository.UserRepository;
import com.brunoli.payroll.analytics.util.DomainModelException;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(Group.class)
public class GroupEventHandler {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;

	@Autowired
	public GroupEventHandler(GroupRepository groupRepository, UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
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
		if (group.getIsDefault()) {
			throw new DomainModelException("Group default cant be deleted.");
		}
		List<User> users = userRepository.findByDomainId(group.getDomainId());
		if (users != null) {
			for (User user : users) {
				if (user.getGroupsIds() != null && user.getGroupsIds().contains(group.getId())) {
					user.getGroupsIds().remove(group.getId());
					userRepository.save(user);
				}
			}
		}
	}

	@HandleAfterDelete
	public void afterDeleteGroup(Group group) {
		System.out.println("after delete: " + group);
	}
}
// end::code[]