package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.mt102.GetMT102Request;
import com.mt102.GetMT102Response;
import com.mt102.MT102;

@Component
public class BankaClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	public void sendNalog(MT102 mt) {
		GetMT102Request mt102 = new GetMT102Request();
		//MT102 mt = new MT102();
		mt102.setMT102(mt);
		GetMT102Response nalogZaPrenosResponse = (GetMT102Response) webServiceTemplate.marshalSendAndReceive(mt102);	
	}
}