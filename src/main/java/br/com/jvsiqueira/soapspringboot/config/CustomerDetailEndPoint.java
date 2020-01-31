package br.com.jvsiqueira.soapspringboot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.com.jvsiqueira.soapspringboot.bean.Customer;
import br.com.jvsiqueira.soapspringboot.exception.CustomerNotFOundException;
import br.com.jvsiqueira.soapspringboot.services.CustomerDetailService;
import br.com.jvsiqueira.soapspringboot.xsd.CustomerDetail;
import br.com.jvsiqueira.soapspringboot.xsd.DeleteCustomerRequest;
import br.com.jvsiqueira.soapspringboot.xsd.DeleteCustomerResponse;
import br.com.jvsiqueira.soapspringboot.xsd.GetAllCustomerDetailRequest;
import br.com.jvsiqueira.soapspringboot.xsd.GetAllCustomerDetailResponse;
import br.com.jvsiqueira.soapspringboot.xsd.GetCustomerDetailRequest;
import br.com.jvsiqueira.soapspringboot.xsd.GetCustomerDetailResponse;
import br.com.jvsiqueira.soapspringboot.xsd.SaveCustomerRequest;
import br.com.jvsiqueira.soapspringboot.xsd.SaveCustomerResponse;

@Endpoint
public class CustomerDetailEndPoint {

	@Autowired
	CustomerDetailService customerDetailService;
	
	@PayloadRoot(namespace="http://xsd.soapspringboot.jvsiqueira.com.br", localPart="GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processCustimerDetail(@RequestPayload GetCustomerDetailRequest req) throws RuntimeException {
		Customer customer = customerDetailService.findById(req.getId());
		if(customer == null) {
			throw new CustomerNotFOundException("Invalid Customer id: " + req.getId());
		}
		return convertToGetCustomerDetailResponse(customer);
	}
	
	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse getCustomerDetailResponse = new GetCustomerDetailResponse();
		getCustomerDetailResponse.setCustomerDetail(convertToCustomerDetail(customer));
		return getCustomerDetailResponse;
	}
	
	private CustomerDetail convertToCustomerDetail(Customer customer) {
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setEmail(customer.getEmail());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setName(customer.getName());
		return customerDetail;
	}
	
	@PayloadRoot(namespace = "http://xsd.soapspringboot.jvsiqueira.com.br", localPart = "GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse processGetAllCustomerDetailResponse(@RequestPayload GetAllCustomerDetailRequest req) {
		List<Customer> customers = customerDetailService.findAll();
		return convertToGetAllCustomerDetailResponse(customers);
	}
	
	
	private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers){
		GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
		customers.stream().forEach(c -> resp.getCustomerDetail().add(convertToCustomerDetail(c)));
		return resp;
	}

	@PayloadRoot(namespace = "http://xsd.soapspringboot.jvsiqueira.com.br", localPart = "DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse delete(@RequestPayload DeleteCustomerRequest req) {
		DeleteCustomerResponse response = new DeleteCustomerResponse();
		response.setStatus(customerDetailService.deleteById(req.getId()));
		return response; 
	}
	
	@PayloadRoot(namespace = "http://xsd.soapspringboot.jvsiqueira.com.br", localPart = "SaveCustomerRequest")
	@ResponsePayload
	public SaveCustomerResponse save(@RequestPayload SaveCustomerRequest req) {
		SaveCustomerResponse response = new SaveCustomerResponse();
		response.setStatus(customerDetailService.save(convertToCustomer(req.getCustomerDetail())));
		return response;
	}
	
	private Customer convertToCustomer(CustomerDetail cd) {
		Customer customer = new Customer(cd.getId(), cd.getName(), cd.getPhone(), cd.getEmail()); 
		return customer;
	}
	
}
