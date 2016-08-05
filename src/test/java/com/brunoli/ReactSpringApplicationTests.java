package com.brunoli;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.brunoli.payroll.analytics.entities.Domain;
import com.brunoli.payroll.analytics.entities.Group;
import com.brunoli.payroll.analytics.repository.DomainRepository;
import com.brunoli.payroll.analytics.repository.GroupRepository;
import com.brunoli.payroll.analytics.repository.UserRepository;

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
