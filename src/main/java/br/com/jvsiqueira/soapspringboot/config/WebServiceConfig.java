package br.com.jvsiqueira.soapspringboot.config;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter{
	
	// CONFIGURAÇÃO DO WSDL
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
	}
	
	// CONFIGURAÇÃO DO WSDL
	@Bean
	public XsdSchema customerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/cliente.xsd"));
	}
	
	// CONFIGURAÇÃO DO WSDL
	@Bean(name="customers") // localhost:8080/ws/customers.wsdl
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customerSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("CustomerPort");
		definition.setTargetNamespace("http://xsd.soapspringboot.jvsiqueira.com.br");
		definition.setLocationUri("/ws");
		definition.setSchema(customerSchema);
		return definition;
	}
	
	// CONFIGURAÇÃO DO SECURITY
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor interceptor = new XwsSecurityInterceptor();
		interceptor.setCallbackHandler(callbackHandler());
		interceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return interceptor;
	}
	
	// CONFIGURAÇÃO DO SECURITY
	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsersMap(Collections.singletonMap("teste", "123"));
		return handler;
	}
	
	// CONFIGURAÇÃO DO SECURITY
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceprots) {
		interceprots.add(securityInterceptor());
	}
	
}
