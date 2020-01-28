package br.com.jvsiqueira.soapspringboot.services;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.com.jvsiqueira.CustomerDetail;
import br.com.jvsiqueira.GetCustomerDetailRequest;
import br.com.jvsiqueira.GetCustomerDetailResponse;

@Endpoint
public class CustomerDetailEndPoint {

	@PayloadRoot(namespace="http://jvsiqueira.com.br", localPart="GetCustomerDetailRequest")
	//@ResponsePayload
	public GetCustomerDetailResponse processCustimerDetail(/*@ResquestPayLoad*/GetCustomerDetailRequest req) {
		GetCustomerDetailResponse response = new GetCustomerDetailResponse();
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(req.getId());
		response.setCustomerDetail(customerDetail);
		return response;
	}
}
