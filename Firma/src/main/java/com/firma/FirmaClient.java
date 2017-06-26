package com.firma;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.banka.Banka;
import com.banka.BankaService;
import com.faktura.Faktura;
import com.firmas.Firmas;
import com.itextpdf.text.log.SysoCounter;
import com.nalog.GetNalogRequest;
import com.nalog.GetNalogResponse;
import com.nalog.Nalog;
import com.nalog.NalogService;
import com.presek.GetPresekResponse;
import com.racun.Racun;
import com.zahtevZaDobijanjeNaloga.GetZahtevZaDobijanjeNalogaRequest;
import com.zahtevZaDobijanjeNaloga.GetZahtevZaDobijanjeNalogaResponse;
import com.zahtevZaDobijanjeNaloga.ZahtevZaDobijanjeNaloga;
import com.zahtevzadobijanjeizvoda.GetZahtevZaDobijanjeIzvodaRequest;
import com.zahtevzadobijanjeizvoda.ZahtevZaDobijanjeIzvoda;

@Component
public class FirmaClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private NalogService nalogService;

	@Autowired
	private BankaService bankService;
	
	@Autowired
	private FirmaService firmaService;

/*	public void sendNalog(Faktura f) {
		GetNalogRequest nalogRequest =  new GetNalogRequest();
		Nalog nalog = new Nalog();
		nalog.setSvrhaPlacanja("aaa");
		nalogRequest.setNalog(nalog);

		Firmas firmas = (Firmas) httpSession.getAttribute("user");
		String uri = firmas.getFirma().getUri();
		webServiceTemplate.setDefaultUri(uri);
		
		if(!ValidacijaSema.validirajSemu(nalogRequest, "nalog")) {
			System.out.println("Nevalidan dokument");
		}
		else {
			GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
		}
	}*/
	
	public void sendNalogTemp(Nalog nalog2) { //ISPRAVNA 		
		
		GetNalogRequest nalogRequest =  new GetNalogRequest();
		//Nalog nalog = nalogService.findOne(1l);
		nalogRequest.setNalog(nalog2);
		Firmas firmas = (Firmas) httpSession.getAttribute("user");
		List<Banka> banke = bankService.findAll();
		
		String uri = "";
		for(int i =0;i<banke.size();i++) {
			List<Racun> racuni =  banke.get(i).getRacuni();	
			for(int j =0;j<racuni.size();j++) {
				if(racuni.get(j).getBrojRacuna().equals(firmas.getFirma().getRacun().getBrojRacuna()))
					uri = banke.get(i).getUri() + "/ws";
			}
		}
		
		webServiceTemplate.setDefaultUri(uri);
		
		if(!ValidacijaSema.validirajSemu(nalogRequest, "nalog")) {
			System.out.println("Nevalidan nalog");
		}else{
		GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
		}
	}

	
	/*public void sendNalogProvjeraMT() {

		GetNalogRequest nalogRequest =  new GetNalogRequest();
		Nalog nalog = nalogService.findOne(1l);
		nalogRequest.setNalog(nalog);
		
		String kod = nalog.getRacunDuznika().substring(0, 3);
		Banka banka = bankService.findByKod(kod);

		String uri = banka.getUri()+ "/ws";
		
		webServiceTemplate.setDefaultUri(uri);
		if(!ValidacijaSema.validirajSemu(nalogRequest, "nalog")) {
			System.out.println("Nevalidan nalog");
		}
		else {
			GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
		}
	}*/
	
	public GetPresekResponse findPreseke(ZahtevZaDobijanjeIzvoda zahtev) {
		Firmas firmas = (Firmas) httpSession.getAttribute("user");
		Firma firma = firmaService.findOne(firmas.getFirma().getId());
		zahtev.setBrojRacuna(firma.getRacun().getBrojRacuna());
		
		String uri = "";
		for(int i=0;i<bankService.findAll().size();i++) {
			for(int j=0;j<bankService.findAll().get(i).getRacuni().size();j++) {
				if(bankService.findAll().get(i).getRacuni().get(j).getBrojRacuna().equals(firma.getRacun().getBrojRacuna())) {
					uri = bankService.findAll().get(i).getUri()+ "/ws";					
				}
				
			}
		}	
		
		GetZahtevZaDobijanjeIzvodaRequest request = new GetZahtevZaDobijanjeIzvodaRequest();
		request.setZahtevZaDobijanjeIzvoda(zahtev);
		webServiceTemplate.setDefaultUri(uri);
 		GetPresekResponse response = (GetPresekResponse) webServiceTemplate.marshalSendAndReceive(request);
		return response;
	}


	public List<Nalog> getlistaNaloga() { 
		List<Nalog> nalozi = new ArrayList<Nalog>();
		
		Firmas firmas = (Firmas) httpSession.getAttribute("user");
		Firma firma = firmaService.findOne(firmas.getFirma().getId());
		
		GetZahtevZaDobijanjeNalogaRequest request = new GetZahtevZaDobijanjeNalogaRequest();
		ZahtevZaDobijanjeNaloga zahtevZaNaloge = new ZahtevZaDobijanjeNaloga();
		zahtevZaNaloge.setBrojRacuna(firma.getRacun().getBrojRacuna());
		
		request.setZahtevZaDobijanjeNaloga(zahtevZaNaloge);
		
		String uri = "";
		for(int i=0;i<bankService.findAll().size();i++) {
			for(int j=0;j<bankService.findAll().get(i).getRacuni().size();j++) {
				if(bankService.findAll().get(i).getRacuni().get(j).getBrojRacuna().equals(firma.getRacun().getBrojRacuna())) {
					uri = bankService.findAll().get(i).getUri()+ "/ws";					
				}
				
			}
		}	
		webServiceTemplate.setDefaultUri(uri);
 		GetZahtevZaDobijanjeNalogaResponse response = (GetZahtevZaDobijanjeNalogaResponse) webServiceTemplate.marshalSendAndReceive(request);
		
 		nalozi = response.getNalog();
		
		return nalozi;
		
	}
}