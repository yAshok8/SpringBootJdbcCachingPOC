package com.example.springdb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.springdb.entities.Customer;
import com.example.springdb.repository.CustomerDao;

@RestController
@RequestMapping("/api/rest")
public class CustomerRestController {
	
	@Autowired
	private CustomerDao dao;

	public CustomerDao getDao() {
		return dao;
	}

	public void setDao(CustomerDao dao) {
		this.dao = dao;
	}

	@RequestMapping("/customers")
	public List<Customer> getAllCustomers(){
		return getDao().getCustomers();
	}

	@RequestMapping("/customers/{id}")
	public Customer getCustomerById(@PathVariable("id") Long id) {
		return getDao().getCustomerById(id);
	}
	
	@ResponseStatus(value=HttpStatus.CREATED)
	@RequestMapping(value="/customers", method=RequestMethod.POST)
	public Customer insertCustomer(@RequestBody Customer customer) {
		return getDao().insertCustomer(customer);
	}
}
