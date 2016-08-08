package com.brunoli;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.brunoli.payroll.analytics.entities.Domain;
import com.brunoli.payroll.analytics.repository.DomainRepository;
import com.brunoli.payroll.analytics.repository.GroupRepository;
import com.brunoli.payroll.analytics.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReactSpringApplication.class)
@WebAppConfiguration
public class ReactSpringApplicationTestsViaREST {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private String userName = "bdussault";

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private DomainRepository domainRepository;

	private List<Domain> domainList = new ArrayList<>();

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		userRepository.deleteAll();
		groupRepository.deleteAll();
		domainRepository.deleteAll();

		Domain domain10 = new Domain();
		domain10.setName("domain10");
		domain10.setDescription("desc10");
		Domain domain11 = new Domain();
		domain11.setName("domain11");
		domain11.setDescription("desc11");
		domainList.add(domainRepository.save(domain10));
		domainList.add(domainRepository.save(domain11));
	}

	@Test
	public void noDomains() throws Exception {
		mockMvc.perform(get("/api/domains").content(this.json(new ArrayList<Domain>())).contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public void createDomain() throws Exception {
		Domain domain = new Domain();
		domain.setName("domain1");
		domain.setDescription("desc1");
		String domainJson = json(domain);
		ResultActions result = this.mockMvc.perform(post("/api/domains").contentType(contentType).content(domainJson));
		result.andExpect(status().isCreated());
	}

	@Test
	public void createDomainInvalid() throws Exception {
		Domain domain = new Domain();
		domain.setDescription("desc1");
		String domainJson = json(domain);
		ResultActions result = this.mockMvc.perform(post("/api/domains").contentType(contentType).content(domainJson));
		result.andExpect(status().isBadRequest());
		mockMvc.perform(get("/api/domains")).andExpect(status().isOk()).andExpect(jsonPath("$['page']['totalElements']").value(2));
		mockMvc.perform(get("/api/domains")).andExpect(status().isOk()).andExpect(jsonPath("$['_embedded']['domains']", hasSize(2)));
	}

	@Test
	public void createDuplicatedDomain() throws Exception {
		Domain domain = new Domain();
		domain.setName("domain2");
		domain.setDescription("desc2");
		String domainJson = json(domain);
		ResultActions result = this.mockMvc.perform(post("/api/domains").contentType(contentType).content(domainJson));
		result.andExpect(status().isCreated());
		result = this.mockMvc.perform(post("/api/domains").contentType(contentType).content(domainJson));
		result.andExpect(status().isConflict());
	}

	@Test
	public void getDomain() throws Exception {
		mockMvc.perform(get("/api/domains")).andExpect(status().isOk()).andExpect(jsonPath("$['page']['totalElements']").value(2));
		mockMvc.perform(get("/api/domains")).andExpect(status().isOk()).andExpect(jsonPath("$['_embedded']['domains']", hasSize(2)));
	}
	
	@Test
	public void getDomainByDesc() throws Exception {
		//http://localhost:8080/api/domains/search/byDesc?description=desc11
		mockMvc.perform(get("/api/domains/search/byDesc?description=desc11")).andExpect(status().isOk()).andExpect(jsonPath("$['_embedded']['domains']", hasSize(1)));
	}
	
	

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
