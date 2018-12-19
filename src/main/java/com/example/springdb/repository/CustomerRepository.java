package com.example.springdb.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.springdb.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
