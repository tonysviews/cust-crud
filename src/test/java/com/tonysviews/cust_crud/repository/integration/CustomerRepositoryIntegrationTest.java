package com.tonysviews.cust_crud.repository.integration;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tonysviews.cust_crud.App;
import com.tonysviews.cust_crud.model.Customer;
import com.tonysviews.cust_crud.repository.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class CustomerRepositoryIntegrationTest {

	private static final long ID = 1L;
	private static final String PHONE_NUMBER = "86-(723)867-8739";
	private static final String STREET_ADDRESS = "48 Parkside Way";
	
	private static final String PHONE_NUMBER2 = "123-1234";
	private static final String STREET_ADDRESS2 = "123 ABC Street";
	
	
	@Autowired
	private CustomerRepository repository;

	@Test
	public void findAll() {
		List<Customer> customers = repository.findAll();
		assertThat(customers.size(), is(greaterThanOrEqualTo(10)));
	}
	
	@Test
	public void retrieve(){
		Customer customer = repository.findOne(1L);
		assertThat(customer.getId(), is(ID));
		assertThat(customer.getAddress(), is(STREET_ADDRESS));
		assertThat(customer.getPhoneNumber(), is(PHONE_NUMBER));
	}
	
	
	@Test
	public void create(){
		Customer customer = new Customer(STREET_ADDRESS2, PHONE_NUMBER2);
		Customer result = repository.saveAndFlush(customer);
		assertThat(result.getId(), is(greaterThan(10L)));
		assertThat(result.getAddress(), is(STREET_ADDRESS2));
		assertThat(result.getPhoneNumber(), is(PHONE_NUMBER2));
		repository.delete(result);
	}
	
	@Test
	public void delete(){
		Customer customer = new Customer(STREET_ADDRESS2, PHONE_NUMBER2);
		Customer result = repository.saveAndFlush(customer);
		assertThat(result.getId(), is(greaterThan(10L)));
		assertThat(result.getAddress(), is(STREET_ADDRESS2));
		assertThat(result.getPhoneNumber(), is(PHONE_NUMBER2));
		repository.delete(result);
	}

}
