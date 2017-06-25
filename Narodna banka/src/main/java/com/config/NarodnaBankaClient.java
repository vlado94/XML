package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.banka.BankaService;
import com.endopoints.ValidacijaSema;
import com.mt102.GetMT102Request;
import com.mt102.MT102;
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

	public boolean send910(MT910 mt, MT103 mt103, MT102 mt102) {
		GetMT910Request mt910 = new GetMT910Request();
		mt910.setMT910(mt);

		String swiftKod = mt.getSwiftKodBankePoverioca();
		String uri = bankaService.findBySwiftKod(swiftKod).getUri() + "/ws";
		webServiceTemplate.setDefaultUri(uri);
		if(!ValidacijaSema.validirajSemu(mt910, "mt910")) {
			System.out.println("Nevalidan dokument");
			return false;
		}
		GetMT910Response nalogZaPrenosResponse = (GetMT910Response) webServiceTemplate.marshalSendAndReceive(mt910);

		if (mt103 != null) {
			GetMT103Request mt103Request = new GetMT103Request();
			mt103Request.setMT103(mt103);
			if(!ValidacijaSema.validirajSemu(mt103Request, "mt103")) {
				System.out.println("Nevalidan dokument");
				return false;
			}
			GetMT910Response responseOdMT103 = (GetMT910Response) webServiceTemplate
				.marshalSendAndReceive(mt103Request);
		} else if (mt102 != null) {
			GetMT102Request mt102Request = new GetMT102Request();
			// to do
			mt102Request.setMT102(mt102);
			if(!ValidacijaSema.validirajSemu(mt102Request, "mt102")) {
				System.out.println("Nevalidan dokument");
				return false;
			}
			GetMT910Response responseOdMT102 = (GetMT910Response) webServiceTemplate
				.marshalSendAndReceive(mt102Request);

		}
		return true;
	}

}
