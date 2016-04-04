package com.tonysviews.cust_crud.controller.client.integration;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonysviews.cust_crud.App;
import com.tonysviews.cust_crud.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest
public class CustomerControllerClientIntegrationTest {

	private static final long ID = 1L;
	private static final String PHONE_NUMBER = "86-(723)867-8739";
	private static final String STREET_ADDRESS = "48 Parkside Way";

	private static final String STREET_ADDRESS2 = "123 ABC Street";
	private static final String PHONE_NUMBER2 = "555-5555";
	private static final String UPDATED = "_updated";

	@Test
	public void list() throws IOException {
		RestTemplate template = new TestRestTemplate();
		ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/v1/customers", String.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		ObjectMapper objectMapper = new ObjectMapper();
		List<Customer> customers = objectMapper.readValue(response.getBody(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));

		assertThat(customers.size(), is(greaterThanOrEqualTo(10)));
		Customer customer = customers.get(0);
		assertThat(customer.getId(), is(ID));
		assertThat(customer.getAddress(), is(STREET_ADDRESS));
		assertThat(customer.getPhoneNumber(), is(PHONE_NUMBER));
	}

	@Test
	public void retrieve() throws IOException {
		RestTemplate template = new TestRestTemplate();
		ResponseEntity<String> response = template.getForEntity("http://localhost:8080/api/v1/customers/1",
				String.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		ObjectMapper objectMapper = new ObjectMapper();
		Customer customer = objectMapper.readValue(response.getBody(), Customer.class);

		assertThat(customer.getId(), is(ID));
		assertThat(customer.getAddress(), is(STREET_ADDRESS));
		assertThat(customer.getPhoneNumber(), is(PHONE_NUMBER));
	}

	@Test
	public void create() throws IOException{
		Customer newCustomer = new Customer(STREET_ADDRESS2, PHONE_NUMBER2);
		ObjectMapper objectMapper  = new ObjectMapper();
		
		RestTemplate template = new TestRestTemplate();
		ResponseEntity<String> response = template.postForEntity("http://localhost:8080/api/v1/customers", newCustomer, String.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		Customer customer = objectMapper.readValue(response.getBody(), Customer.class);
		assertThat(customer.getId(), is(greaterThan(0L)));
		assertThat(customer.getAddress(), is(STREET_ADDRESS2));
		assertThat(customer.getPhoneNumber(), is(PHONE_NUMBER2));
		
		template.delete("http://localhost:8080/api/v1/customers/" + customer.getId());
	}
	
	@Test
	public void update() throws IOException{
		Customer newCustomer = new Customer(STREET_ADDRESS2, PHONE_NUMBER2);
		ObjectMapper objectMapper  = new ObjectMapper();
		
		RestTemplate template = new TestRestTemplate();
		ResponseEntity<String> response = template.postForEntity("http://localhost:8080/api/v1/customers", newCustomer, String.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		Customer customer = objectMapper.readValue(response.getBody(), Customer.class);
		long newId = customer.getId();
		assertThat(newId, is(greaterThan(0L)));
		assertThat(customer.getAddress(), is(STREET_ADDRESS2));
		assertThat(customer.getPhoneNumber(), is(PHONE_NUMBER2));
		
		newCustomer.setId(customer.getId());
		newCustomer.setAddress(STREET_ADDRESS2 + UPDATED);
		newCustomer.setPhoneNumber(PHONE_NUMBER2 + UPDATED);
		
		template.put("http://localhost:8080/api/v1/customers/" + newId, newCustomer);
		
		response = template.getForEntity("http://localhost:8080/api/v1/customers/" + newId, String.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		customer = objectMapper.readValue(response.getBody(), Customer.class);
		assertThat(customer.getAddress(), is(STREET_ADDRESS2 + UPDATED));
		assertThat(customer.getPhoneNumber(), is(PHONE_NUMBER2 + UPDATED));
		
		template.delete("http://localhost:8080/api/v1/customers/" + newId);
		
		response = template.getForEntity("http://localhost:8080/api/v1/customers/" + newId, String.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void delete() throws IOException{
		Customer newCustomer = new Customer(STREET_ADDRESS2, PHONE_NUMBER2);
		ObjectMapper objectMapper  = new ObjectMapper();
		
		RestTemplate template = new TestRestTemplate();
		ResponseEntity<String> response = template.postForEntity("http://localhost:8080/api/v1/customers", newCustomer, String.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		Customer customer = objectMapper.readValue(response.getBody(), Customer.class);
		long newId = customer.getId();
		assertThat(newId, is(greaterThan(0L)));
		assertThat(customer.getAddress(), is(STREET_ADDRESS2));
		assertThat(customer.getPhoneNumber(), is(PHONE_NUMBER2));
		
		template.delete("http://localhost:8080/api/v1/customers/" + newId);
		
		response = template.getForEntity("http://localhost:8080/api/v1/customers/" + newId, String.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}

}
