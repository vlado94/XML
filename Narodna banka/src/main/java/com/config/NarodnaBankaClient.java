package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.banka.Banka;
import com.banka.BankaService;
import com.mt103.GetMT103Request;
import com.mt103.MT103;
import com.mt910.GetMT910Request;
import com.mt910.GetMT910Response;
import com.mt910.MT910;

@Component
public class NarodnaBankaClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@Autowired
	private BankaService bankaService;

	public void send910(MT910 mt, MT103 mt103) {
		GetMT910Request mt910 = new GetMT910Request();

		mt910.setMT910(mt);
		
		GetMT103Request mt103Request = new GetMT103Request();
		mt103Request.setMT103(mt103);

		String swiftKod = mt.getSwiftKodBankePoverioca();
		Banka banka = bankaService.findBySwiftKod(swiftKod);

		String uri = banka.getUri() + "/ws";

		webServiceTemplate.setDefaultUri(uri); // treba namjestiti kojoj banci
												// salje
		
	//	System.out.println("Nalog Prostor izmedju poziva dva servisa"+responseOdMT103.getMT910().toString());

		GetMT910Response nalogZaPrenosResponse = (GetMT910Response) webServiceTemplate.marshalSendAndReceive(mt910);
		GetMT910Response responseOdMT103 = (GetMT910Response) webServiceTemplate.marshalSendAndReceive(mt103Request);
		

	}
	
	
}
