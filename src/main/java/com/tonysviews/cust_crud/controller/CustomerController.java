package com.tonysviews.cust_crud.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tonysviews.cust_crud.exceptions.InvalidCustomerException;
import com.tonysviews.cust_crud.model.Customer;
import com.tonysviews.cust_crud.repository.CustomerRepository;

/**
 * The controller for the customer entity.  It has the API endpoint:
 * /api/v1/customers
 * 
 * @author Tony
 *
 */
@RestController
@RequestMapping("api/v1")
public class CustomerController {

	@Autowired
	private CustomerRepository repository;

	/**
	 * Retrieves all customers
	 * 
	 * @return a list customers
	 */
	@RequestMapping(value = "customers", method = RequestMethod.GET)
	public List<Customer> list() {
		return repository.findAll();
	}

	/**
	 * Creates a new customer entity
	 * 
	 * @param customer a customer object that contains the new customer details
	 * @return the newly created customer
	 */
	@RequestMapping(value = "customers", method = RequestMethod.POST)
	public Customer create(@RequestBody Customer customer) {
		return repository.saveAndFlush(customer);
	}

	/**
	 * Fetch a specific customer
	 * 
	 * @param id the specific customer id of the customer to be retrieved
	 * @return the customer retrieved from the repository
	 */
	@RequestMapping(value = "customers/{id}", method = RequestMethod.GET)
	public Customer retrieve(@PathVariable Long id) {
		return repository.findOne(id);
	}

	/**
	 * Update a specific customer's details
	 * @param id the specific customer id whose details will be updated
	 * @param customer the customer object that contains the updated customer details
	 * @return the updated customer details
	 */
	@RequestMapping(value = "customers/{id}", method = RequestMethod.PUT)
	public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
		Customer existingCustomer = repository.findOne(id);
		if(existingCustomer == null){
			throw new InvalidCustomerException();
		}
		BeanUtils.copyProperties(customer, existingCustomer);
		return repository.saveAndFlush(existingCustomer);
	}

	/**
	 * Deletes the specific customer
	 * @param id the specific id of the customer to be deleted
	 * @return the details of the customer that was deleted.
	 */
	@RequestMapping(value = "customers/{id}", method = RequestMethod.DELETE)
	public Customer delete(@PathVariable Long id) {
		Customer existingCustomer = repository.findOne(id);
		if(existingCustomer == null){
			throw new InvalidCustomerException();
		}
		repository.delete(existingCustomer);
		return existingCustomer;
	}
}
