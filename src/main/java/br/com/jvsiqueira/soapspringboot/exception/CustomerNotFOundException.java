package br.com.jvsiqueira.soapspringboot.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

// CÓDIGO COMENTADO, REFERE-SE À CRIAR UM RETORNO COM O CÓDIGO PERSONALIZADO
//faultCode = FaultCode.CUSTOM customFaultCode = "{http://xsd.soapspringboot/jvsiqueira/com/br}001_Customer_Not_Found"
@SoapFault(faultCode = FaultCode.CLIENT) 
public class CustomerNotFOundException extends RuntimeException {

	public CustomerNotFOundException(String message) {
		super(message);
	}
	
}
