package com.endopoints;

import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mt102.GetMT102Request;
import com.mt102.GetMT102Response;

@Endpoint
@Component
public class NarodnaBankaEndpoint {
	private static final String NAMESPACE_URI = "http://mt102.com";
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMT102Request")
	@ResponsePayload
	public GetMT102Response getNalogZaPlacanje(@RequestPayload GetMT102Request request) {
		GetMT102Response response = new GetMT102Response();
		
		System.out.println("u narodnoj banci endpoint" + request.getMT102().getPojedinacnoPlacanjeMT102().size());
		return response;
	}
}