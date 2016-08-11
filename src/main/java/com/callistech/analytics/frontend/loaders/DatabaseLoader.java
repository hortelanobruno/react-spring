package com.callistech.analytics.frontend.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.callistech.analytics.frontend.domains.uc.entities.Domain;
import com.callistech.analytics.frontend.domains.uc.entities.Group;
import com.callistech.analytics.frontend.domains.uc.entities.User;
import com.callistech.analytics.frontend.domains.uc.repository.DomainRepository;
import com.callistech.analytics.frontend.domains.uc.repository.GroupRepository;
import com.callistech.analytics.frontend.domains.uc.repository.UserRepository;
import com.callistech.analytics.frontend.services.SettingsService;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
public class DatabaseLoader implements CommandLineRunner {

	private final SettingsService settingsService;
	private final DomainRepository domainRepository;
	private final UserRepository userRepository;
	private final GroupRepository groupRepository;
	private final String defaultDomainName = "Administrator";
	private final String contextPath = ".";

	@Autowired
	public DatabaseLoader(SettingsService settingsService, DomainRepository domainRepository, UserRepository userRepository, GroupRepository groupRepository) {
		this.settingsService = settingsService;
		this.domainRepository = domainRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Carga inicial");
		Domain domain = domainRepository.findByName(defaultDomainName);
		if (domain == null) {
			domain = new Domain();
			domain.setName(defaultDomainName);
			domain.setDescription("Default description");
			domainRepository.save(domain);

			Group group = new Group();
			group.setName("Admins");
			group.setIsDefault(true);
			group.setDescription("Desc");
			group.setDomainId(domain.getId());
			groupRepository.save(group);

			User user = new User();
			user.setUsername("admin");
			user.setEmail("admin@" + group.getName());
			user.setPassword("callis");
			user.setFullName("El administrador de Callis");
			user.setActive(true);
			user.setIsDefault(true);
			userRepository.save(user);
		}
		settingsService.init(contextPath);
		System.out.println("Fin carga inicial");
	}
}
// end::code[]