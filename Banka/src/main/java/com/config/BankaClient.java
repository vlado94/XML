package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.endopoints.ValidacijaSema;
import com.mt102.GetMT102Request;
import com.mt102.MT102;
import com.mt103.GetMT103Request;
import com.mt103.MT103;
import com.mt900.GetMT900Response;
import com.mt900.MT900;

@Component
public class BankaClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	public MT900 sendMT102(MT102 mt) {
		
		GetMT102Request mt102 = new GetMT102Request();
		mt102.setMT102(mt);
		
		if(!ValidacijaSema.validirajSemu(mt102, "mt102")) {
			System.out.println("Nevalidan mt102 dokument");
			return null;
		}
	
		GetMT900Response nalogZaPrenosResponse = (GetMT900Response) webServiceTemplate.marshalSendAndReceive(mt102);	
		return nalogZaPrenosResponse.getMT900();
	
	}
	
	public MT900 sendMT103(MT103 mt) {
		GetMT103Request mt103 = new GetMT103Request();
		
		mt103.setMT103(mt);
		if(!ValidacijaSema.validirajSemu(mt103, "mt103")) {
			System.out.println("Nevalidan dokument");
			return null;
		}
		GetMT900Response nalogZaPrenosResponse = (GetMT900Response) webServiceTemplate.marshalSendAndReceive(mt103);	
		if(nalogZaPrenosResponse == null || !ValidacijaSema.validirajSemu(nalogZaPrenosResponse, "mt900")) {
			System.out.println("Nevalidan dokument ili nije poslat odgovor");
			return null;
		}
		MT900 mt900 = nalogZaPrenosResponse.getMT900();
		return mt900;
		
	}
}