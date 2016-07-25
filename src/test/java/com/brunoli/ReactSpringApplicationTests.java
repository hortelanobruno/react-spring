package com.brunoli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.brunoli.ReactSpringApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReactSpringApplication.class)
@WebAppConfiguration
public class ReactSpringApplicationTests {

	@Test
	public void contextLoads() {
	}

}
