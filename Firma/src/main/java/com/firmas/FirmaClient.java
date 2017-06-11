package com.firmas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.faktura.Faktura;
import com.nalog.GetNalogRequest;
import com.nalog.GetNalogResponse;
import com.nalog.Nalog;

@Component
public class FirmaClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(BankClient.class);

	public void sendNalog(Faktura f) {
		GetNalogRequest nalogRequest =  new GetNalogRequest();
		Nalog nalog = new Nalog();
		nalog.setSvrhaPlacanja("aaa");
		nalogRequest.setNalog(nalog);
		GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
		
		System.out.println("aaa");
	}

}