package com.brunoli;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.callistech.analytics.frontend.ReactSpringApplication;
import com.callistech.analytics.frontend.domains.uc.entities.Domain;
import com.callistech.analytics.frontend.domains.uc.entities.Group;
import com.callistech.analytics.frontend.domains.uc.repository.DomainRepository;
import com.callistech.analytics.frontend.domains.uc.repository.GroupRepository;
import com.callistech.analytics.frontend.domains.uc.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReactSpringApplication.class)
@WebAppConfiguration
public class ReactSpringApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private DomainRepository domainRepository;

	@Test
	public void removeAll() {
		domainRepository.deleteAll();
		assertEquals(domainRepository.count(), 0l);
	}

	@Test
	public void createDomain() {
		domainRepository.deleteAll();
		groupRepository.deleteAll();
		Domain domain = new Domain();
		domain.setName("domain1");
		domain.setDescription("description1");

		domainRepository.save(domain);

		Group group = new Group();
		group.setName("group1");
		group.setDescription("description1");
		group.setDomainId(domain.getId());

		groupRepository.save(group);

		Domain domain2 = domainRepository.findOne(domain.getId());

		assertEquals(domain, domain2);
	}

}
