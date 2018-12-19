package com.example.springdb.SpringDbExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.springdb.repository.CustomerRepository;

@EnableCaching
@SpringBootApplication
@EnableJpaRepositories("com.example.springdb.repository")
@EntityScan("com.example.springdb.entities")
@ComponentScan({"com.example.springdb.restcontroller", "com.example.springdb.repository"})
public class SpringDbExampleApplication{
	
	@Autowired
	CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDbExampleApplication.class, args);
	}

/*	@Override
	public void run(String... args) throws Exception {
		String sqlStr = "SELECT * FROM CUSTOMER";
		List<Customer> customers = new ArrayList<Customer>();
        List<Map<String, Object>> rows = template.queryForList(sqlStr);

        for (Map<String, Object> row : rows) 
        {
             Customer customer = new Customer();
             customer.setId((Long)row.get("id"));
             customer.setName((String)row.get("name"));
             customers.add(customer);
         }
        
        for(Customer cust : customers)
        	System.out.println(cust.getName());

	}*/

}

