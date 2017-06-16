package com.firmas;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.banka.Banka;
import com.banka.BankaService;
import com.faktura.Faktura;
import com.firma.Firma;
import com.firma.FirmaService;
import com.nalog.GetNalogRequest;
import com.nalog.GetNalogResponse;
import com.nalog.Nalog;
import com.nalog.NalogService;
import com.racun.Racun;
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

	public void sendNalog(Faktura f) {
		GetNalogRequest nalogRequest =  new GetNalogRequest();
		Nalog nalog = new Nalog();
		nalog.setSvrhaPlacanja("aaa");
		nalogRequest.setNalog(nalog);

		Firmas firmas = (Firmas) httpSession.getAttribute("user");
		String uri = firmas.getFirma().getUri();
		webServiceTemplate.setDefaultUri(uri);
		GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
		
		System.out.println("aaa");
	}
	
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
		GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
	}

	
	public void sendNalogProvjeraMT() {

		GetNalogRequest nalogRequest =  new GetNalogRequest();
		Nalog nalog = nalogService.findOne(1l);
		nalogRequest.setNalog(nalog);
		
		String kod = nalog.getRacunDuznika().substring(0, 3);
		Banka banka = bankService.findByKod(kod);

		String uri = banka.getUri()+ "/ws";
		
		webServiceTemplate.setDefaultUri(uri);
 		GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
	
	}
	
	public List<Nalog> findPreseke(ZahtevZaDobijanjeIzvoda zahtev) {
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
		
		
		webServiceTemplate.setDefaultUri(uri);
 		//GetNalogResponse nalogResponse = (GetNalogResponse) webServiceTemplate.marshalSendAndReceive(nalogRequest);
		return null;
	}
}