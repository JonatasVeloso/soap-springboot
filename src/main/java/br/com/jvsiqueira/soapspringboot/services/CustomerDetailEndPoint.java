package br.com.jvsiqueira.soapspringboot.services;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import br.com.jvsiqueira.soapspringboot.xsd.CustomerDetail;
import br.com.jvsiqueira.soapspringboot.xsd.GetCustomerDetailRequest;
import br.com.jvsiqueira.soapspringboot.xsd.GetCustomerDetailResponse;

@Endpoint
public class CustomerDetailEndPoint {

	@PayloadRoot(namespace="http://xsd.soapspringboot.jvsiqueira.com.br", localPart="GetCustomerDetailRequest")
	//@ResponsePayload
	public GetCustomerDetailResponse processCustimerDetail(/*@ResquestPayLoad*/GetCustomerDetailRequest req) {
		GetCustomerDetailResponse response = new GetCustomerDetailResponse();
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(req.getId());
		response.setCustomerDetail(customerDetail);
		return response;
	}
}
