package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.mt102.GetMT102Request;
import com.mt102.GetMT102Response;
import com.mt102.MT102;
import com.mt103.GetMT103Request;
import com.mt103.GetMT103Response;
import com.mt103.MT103;

@Component
public class BankaClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	public void sendMT102(MT102 mt) {
		GetMT102Request mt102 = new GetMT102Request();
		//MT102 mt = new MT102();
		mt102.setMT102(mt);
		GetMT102Response nalogZaPrenosResponse = (GetMT102Response) webServiceTemplate.marshalSendAndReceive(mt102);	
	}
	
	public void sendMT103(MT103 mt) {
		GetMT103Request mt103 = new GetMT103Request();
		//MT102 mt = new MT102();
		mt103.setMT103(mt);
		GetMT103Response nalogZaPrenosResponse = (GetMT103Response) webServiceTemplate.marshalSendAndReceive(mt103);	
	}
}