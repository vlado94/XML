package com.endopoints;

import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mt102.GetMT102Request;
import com.mt102.GetMT102Response;
import com.mt103.GetMT103Request;
import com.mt103.GetMT103Response;

@Endpoint
@Component
public class NarodnaBankaEndpoint {
	private static final String NAMESPACE_URI = "http://mt102.com";
	private static final String NAMESPACE_URI2 = "http://mt103.com";
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMT102Request")
	@ResponsePayload
	public GetMT102Response getNalogZaPlacanje(@RequestPayload GetMT102Request request) {
		GetMT102Response response = new GetMT102Response();
		
		System.out.println("u narodnoj banci endpoint" + request.getMT102().getPojedinacnoPlacanjeMT102().size());
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI2, localPart = "getMT103Request")
	@ResponsePayload
	public GetMT103Response getMT103(@RequestPayload GetMT103Request request) {
		GetMT103Response response = new GetMT103Response();
		System.out.println("U narodnoj MT103" + request.getMT103().getDuznik());
		return response;
	}
}