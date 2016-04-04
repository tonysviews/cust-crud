package com.tonysviews.cust_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tonysviews.cust_crud.model.Customer;

/**
 * Customer Repository that extends the Spring JpaRepository
 * 
 * @author Tony
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
