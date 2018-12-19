package com.example.springdb.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.springdb.entities.Customer;

@Repository
public class CustomerDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Customer> getCustomers(){
		String sql = "SELECT * FROM CUSTOMER";
		RowMapper<Customer> rmRef = (ResultSet rs, int rowNum) -> {
			Customer cust = new Customer();
			cust.setId(rs.getLong("id"));
			cust.setName(rs.getString("name"));
			return cust;
		};
		return getJdbcTemplate().query(sql, rmRef);
	}
	
	@Cacheable("student")
	public Customer getCustomerById(Long customerId) {
		String sql = "SELECT * FROM CUSTOMER WHERE id = ?";
		RowMapper<Customer> rmRef = (ResultSet rs, int rowNum) -> {
			Customer cust = new Customer();
			cust.setId(rs.getLong("id"));
			cust.setName(rs.getString("name"));
			return cust;
		};
		System.out.println("Trip to database");
		return getJdbcTemplate().queryForObject(sql, new Object[] {customerId}, rmRef);
	}
	
	@CachePut(value="student", key="#result.id")
	public Customer insertCustomer(Customer customer) {
/*		String sql = "INSERT INTO CUSTOMER (NAME) VALUES (?)";
		int id = getJdbcTemplate().update(sql, new Object[] {customer.getName()});
		System.out.println(id);*/
		PreparedStatementCreator pCreatorRef = (Connection conn) -> {
			String sql = "INSERT INTO CUSTOMER (NAME) VALUES (?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getName());
			return ps;			
		};
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(pCreatorRef, holder);
		customer.setId(holder.getKey().longValue());
		return customer;
	}

}
