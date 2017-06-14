package com.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.config.BankaClient;
import com.nalog.GetNalogRequest;
import com.nalog.GetNalogResponse;

@Endpoint
@Component
public class BankEndpoint {
	private static final String NAMESPACE_URI = "http://nalog.com";
	
	@Autowired
	BankaClient bankaClient;
	
	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNalogRequest")
	@ResponsePayload
	public GetNalogResponse getNalog(@RequestPayload GetNalogRequest request) {
		GetNalogResponse response = new GetNalogResponse();
		
		System.out.println(webServiceTemplate.getDefaultUri());
		
		//bankaClient.sendNalog();		
		return response;
	}
	
	
}