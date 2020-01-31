package br.com.jvsiqueira.soapspringboot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jvsiqueira.soapspringboot.bean.Customer;
import br.com.jvsiqueira.soapspringboot.xsd.Status;

@Service
public class CustomerDetailService {
	
	private static List<Customer> customers = new ArrayList<>();
	
	static {
		Customer customer1 = new Customer(1,"Bob", "999999", "bob@gmail.com");
		customers.add(customer1);
		
		Customer customer2 = new Customer(2,"Zé", "888888", "ze@gmail.com");
		customers.add(customer2);
		
		Customer customer3 = new Customer(3,"Fred", "1111111", "fredgmail.com");
		customers.add(customer3);
		
		Customer customer4 = new Customer(4,"Yan", "222222", "yan@gmail.com");
		customers.add(customer4);
	}
	
	public Customer findById(int id) {
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		if(customerOptional.isPresent()) {
			return customerOptional.get();
		}
		return null;
	}
	
	public List<Customer> findAll() {
		return customers;
	}
	
	public Status deleteById(int id) {
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		if(customerOptional.isPresent()) {
			customers.remove(customerOptional.get());
			return Status.SUCCESS;
		}
		return Status.FAILURE;
	}
	
	public Status save(Customer customer) {
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == customer.getId()).findAny();
		if(!customerOptional.isPresent()) {
			customers.add(customer);
			return Status.SUCCESS;
		}
		return Status.FAILURE;
	}
	
}
