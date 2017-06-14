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
import com.nalog.Nalog;
import com.racun.Racun;
import com.racun.RacunService;

@Endpoint
@Component
public class BankEndpoint {
	private static final String NAMESPACE_URI = "http://nalog.com";
	
	@Autowired
	BankaClient bankaClient;
	
	
	@Autowired
	RacunService racunService;
	
	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNalogRequest")
	@ResponsePayload
	public GetNalogResponse getNalog(@RequestPayload GetNalogRequest request) {
		GetNalogResponse response = new GetNalogResponse();
		Nalog primljenNalog = request.getNalog();
		
		System.out.println("Duznik u bank endopitn " + primljenNalog.getDuznik());
		System.out.println("Racun duznika u bank endopitn " + primljenNalog.getRacunDuznika());
		
		String kodBanke =primljenNalog.getRacunDuznika().substring(0, 3);
		
		if(primljenNalog.getRacunPrimaoca().substring(0, 3).equals(kodBanke)){ //iz iste tj moje
			System.out.println("Iz iste banke tj moje");
			Racun racunDuznika = racunService.findByBrojRacuna(primljenNalog.getRacunDuznika());
			Racun racunPrimaoca = racunService.findByBrojRacuna(primljenNalog.getRacunPrimaoca());
			
			
			racunDuznika.setRezervisano(primljenNalog.getIznos().negate());
			racunPrimaoca.setRezervisano(primljenNalog.getIznos());
			
			
			System.out.println("Rezervisano za duznika" + racunDuznika.getRezervisano());
			
			racunService.save(racunDuznika);
			racunService.save(racunPrimaoca); //trebalo bi u response u firmi da se 
												//azuriraju racuni
		}else{
			System.out.println("Pravi neku od poruka");
		}
		
		System.out.println(webServiceTemplate.getDefaultUri()); //od narodne banke
		
		//bankaClient.sendNalog();		
		return response;
	}
	
	
}