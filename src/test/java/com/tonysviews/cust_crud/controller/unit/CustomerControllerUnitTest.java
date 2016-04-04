package com.tonysviews.cust_crud.controller.unit;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tonysviews.cust_crud.controller.CustomerController;
import com.tonysviews.cust_crud.exceptions.InvalidCustomerException;
import com.tonysviews.cust_crud.model.Customer;
import com.tonysviews.cust_crud.repository.CustomerRepository;

public class CustomerControllerUnitTest {

	// Mock value constants
	private static final Long ID = 1L;
	private static final String STREET_ADDRESS = "123 ABC Street";
	private static final String PHONE_NUMBER = "555-5555";
	
	private static final Long ID2 = 2L;
	private static final String STREET_ADDRESS2 = "123 DEF Street";
	private static final String PHONE_NUMBER2 = "123-1234";
	
	private static final Long INVALID_ID = 3L;

	@InjectMocks
	private CustomerController controller;

	@Mock
	private CustomerRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void retrieveCustomer() {
		// set mock data
		Customer customer = new Customer(ID, STREET_ADDRESS, PHONE_NUMBER);
		when(repository.findOne(1L)).thenReturn(customer);

		// call the controller method
		Customer result = controller.retrieve(ID);

		// assertions
		verify(repository).findOne(ID);
		assertThat(result.getId(), is(ID));
		assertThat(result.getAddress(), is(STREET_ADDRESS));
		assertThat(result.getPhoneNumber(), is(PHONE_NUMBER));
	}

	@Test
	public void createCustomer() {
		// set mock data
		Customer customer = new Customer(ID, STREET_ADDRESS, PHONE_NUMBER);
		when(repository.saveAndFlush(customer)).thenReturn(customer);

		// call the controller method
		Customer result = controller.create(customer);

		// assertions
		verify(repository).saveAndFlush(customer);
		assertThat(result.getId(), is(ID));
		assertThat(result.getAddress(), is(STREET_ADDRESS));
		assertThat(result.getPhoneNumber(), is(PHONE_NUMBER));

	}

	@Test
	public void getCustomerList() {
		// set mock data
		Customer customer1 = new Customer(ID, STREET_ADDRESS, PHONE_NUMBER);
		Customer customer2 = new Customer(ID2, STREET_ADDRESS2, PHONE_NUMBER2);
		List<Customer> customers = new ArrayList<>();
		customers.add(customer1);
		customers.add(customer2);
		when(repository.findAll()).thenReturn(customers);
		
		// call the controller method
		List<Customer> results = controller.list();
		
		// assertions
		verify(repository).findAll();
		assertThat(results.size(), is(2));
		assertThat(results.get(0).getId(), is(ID));
		assertThat(results.get(0).getAddress(), is(STREET_ADDRESS));
		assertThat(results.get(0).getPhoneNumber(), is(PHONE_NUMBER));
		
		assertThat(results.get(1).getId(), is(ID2));
		assertThat(results.get(1).getAddress(), is(STREET_ADDRESS2));
		assertThat(results.get(1).getPhoneNumber(), is(PHONE_NUMBER2));
	}
	
	@Test
	public void updateCustomer() {
		// set mock data
		Customer customer = new Customer(ID, STREET_ADDRESS, PHONE_NUMBER);
		when(repository.findOne(1L)).thenReturn(customer);
		when(repository.saveAndFlush(customer)).thenReturn(customer);

		// call the controller method
		Customer result = controller.update(ID, customer);

		// assertions
		verify(repository).findOne(ID);
		verify(repository).saveAndFlush(customer);
		assertThat(result.getId(), is(ID));
		assertThat(result.getAddress(), is(STREET_ADDRESS));
		assertThat(result.getPhoneNumber(), is(PHONE_NUMBER));
	}
	
	@Test(expected=InvalidCustomerException.class)
	public void updateCustomerWithInvalidId() {
		// set mock data
		Customer customer = new Customer(ID, STREET_ADDRESS, PHONE_NUMBER);

		// call the controller method
		Customer result = controller.update(INVALID_ID, customer);

		// assertions
		verify(repository, times(0)).findOne(ID);
		verify(repository, times(0)).saveAndFlush(customer);
		assertThat(result, is(nullValue()));
	}
	
	@Test
	public void deleteCustomer() {
		// set mock response
		Customer customer = new Customer(ID, STREET_ADDRESS, PHONE_NUMBER);
		when(repository.findOne(1L)).thenReturn(customer);
		
		// call the controller method
		Customer result = controller.delete(ID);

		// assertions
		verify(repository).findOne(ID);
		verify(repository).delete(customer);
		assertThat(result.getId(), is(ID));
		assertThat(result.getAddress(), is(STREET_ADDRESS));
		assertThat(result.getPhoneNumber(), is(PHONE_NUMBER));
	}
	
	@Test(expected=InvalidCustomerException.class)
	public void deleteCustomerWithInvalidId() {
		// set mock data
		Customer customer = new Customer(ID, STREET_ADDRESS, PHONE_NUMBER);

		// call the controller method
		Customer result = controller.delete(INVALID_ID);

		// assertions
		verify(repository, times(0)).findOne(ID);
		verify(repository, times(0)).saveAndFlush(customer);
		assertThat(result, is(nullValue()));
	}

}
