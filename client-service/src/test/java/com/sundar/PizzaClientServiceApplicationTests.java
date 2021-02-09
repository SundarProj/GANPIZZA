package com.sundar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PizzaClientServiceApplicationTests {

	@Autowired
	private ClientController clientController;
	
	@Test
	void contextLoads() {
		assertThat(clientController).isNotNull();
	}

}
