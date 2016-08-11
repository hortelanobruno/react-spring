package com.callistech.analytics.frontend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.callistech.analytics.frontend.domains.uc.entities.Group;
import com.callistech.analytics.frontend.domains.uc.repository.DomainRepository;
import com.callistech.analytics.frontend.domains.uc.repository.GroupRepository;
import com.callistech.analytics.frontend.domains.uc.repository.UserRepository;
import com.callistech.analytics.frontend.domains.uc.util.Permission;
import com.callistech.analytics.frontend.domains.uc.util.PermissionType;

@Component
public class UserControlService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DomainRepository domainRepository;

	@Autowired
	private GroupRepository groupRepository;

	public List<Permission> getPermissionsByUsername(String username) {
		// TODO esto despues sale de la db
		List<Permission> result = new ArrayList<Permission>();
		Permission p;
		for (PermissionType permission : PermissionType.values()) {
			p = new Permission();
			p.setModule(permission.getId());
			p.setPermission(2);
			result.add(p);
		}
		return result;
	}

	public List<Integer> getTemplate_idsByUsername(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	public Group getGroupByName(String name) {
		return groupRepository.findByName(name);
	}

}
