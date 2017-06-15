package com.endopoints;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.config.NarodnaBankaClient;
import com.mt102.GetMT102Request;
import com.mt102.GetMT102Response;
import com.mt103.GetMT103Request;
import com.mt103.MT103Service;
import com.mt900.GetMT900Response;
import com.mt900.MT900;
import com.mt910.MT910;

@Endpoint
@Component
public class NarodnaBankaEndpoint {
	
	@Autowired
	MT103Service MT103Service;
	
	@Autowired
	NarodnaBankaClient narodnaBankaClient;
	
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
	public GetMT900Response getMT103(@RequestPayload GetMT103Request request) {
		GetMT900Response response = new GetMT900Response();
		System.out.println("U narodnoj MT103" + request.getMT103().getDuznik());
		
		
		MT103Service.save(request.getMT103());
		
		MT900 mt900 = new MT900();
		mt900.setIdPoruke((UUID.randomUUID().toString()));
		mt900.setSwiftKodBankeDuznika(request.getMT103().getSwiftKodBankeDuznika());
		mt900.setObracunskiRacunBankeDuznika(request.getMT103().getObracunskiRacunBankeDuznika());    
		mt900.setIdPorukeNaloga(request.getMT103().getIdPoruke());   
		mt900.setDatumValute(request.getMT103().getDatumValute());
		mt900.setIznos(request.getMT103().getIznos());
		mt900.setSifraValute(request.getMT103().getSifraValute());
		
		response.setMT900(mt900);
		//treba da posalje 910 banci primaocu
		MT910 mt910 = new MT910();
		mt910.setIdPoruke((UUID.randomUUID().toString()));
		mt910.setSwiftKodBankePoverioca(request.getMT103().getSwiftKodBankePoverioca());
		mt910.setObracunskiRacunBankePoverioca(request.getMT103().getObracunskiRacunBankePoverioca());    
		mt910.setIdPorukeNaloga(request.getMT103().getIdPoruke());    
		mt910.setDatumValute(request.getMT103().getDatumValute());
		mt910.setIznos(request.getMT103().getIznos());
		mt910.setSifraValute(request.getMT103().getSifraValute());
	
		narodnaBankaClient.send910(mt910, request.getMT103());
		
		return response;
	}
}