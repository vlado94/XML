package com.endopoints;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.banka.Banka;
import com.banka.BankaService;
import com.config.BankaClient;
import com.mt103.MT103;
import com.mt103.MT103Service;
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
	BankaService bankaService;
	@Autowired
	RacunService racunService;
	
	@Autowired
	MT103Service MT103Service;
	
	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNalogRequest")
	@ResponsePayload
	public GetNalogResponse getNalog(@RequestPayload GetNalogRequest request) {
		GetNalogResponse response = new GetNalogResponse();
		Nalog primljenNalog = request.getNalog();
		
		System.out.println("Duznik u bank endopitn " + primljenNalog.getDuznik());
		System.out.println("Racun duznika u bank endopitn " + primljenNalog.getRacunDuznika());
		
		String kodBankeDuznika =primljenNalog.getRacunDuznika().substring(0, 3);
		String kodBankePrimaoca = primljenNalog.getRacunPrimaoca().substring(0, 3);
		
		
		Banka bankaDuznika = bankaService.findByKod(kodBankeDuznika);
		Banka bankaPrimaoca = bankaService.findByKod(kodBankePrimaoca);
		
		Racun racunDuznika = racunService.findByBrojRacuna(primljenNalog.getRacunDuznika());
		Racun racunPrimaoca = racunService.findByBrojRacuna(primljenNalog.getRacunPrimaoca());
		
		if(kodBankePrimaoca.equals(kodBankeDuznika)){ //iz iste tj moje
			System.out.println("Iz iste banke tj moje");
			
			
			MathContext mc = new MathContext(2);
			BigDecimal  bg3 = racunDuznika.getTrenutnoStanje().subtract(primljenNalog.getIznos(), mc);
			racunDuznika.setTrenutnoStanje(bg3);
			
			System.out.println("Racuna za duznika" + racunDuznika.getTrenutnoStanje());
			racunService.save(racunDuznika);
			
			BigDecimal noviPrimaoca  = racunPrimaoca.getTrenutnoStanje().add(primljenNalog.getIznos());
			racunPrimaoca.setTrenutnoStanje(noviPrimaoca);
			
			System.out.println("Racuna za primaoica" + racunPrimaoca.getTrenutnoStanje());
			racunService.save(racunPrimaoca);
			
		}else{
			System.out.println("Pravi neku od poruka");
			
			if(primljenNalog.isHitno()){//hitno
				System.out.println("Hitno");
				
				MT103 mt103 = new MT103();
				
				mt103.setIdPoruke((UUID.randomUUID().toString()));
				mt103.setSwiftKodBankeDuznika(bankaDuznika.getSwiftKod());
				mt103.setObracunskiRacunBankeDuznika(bankaDuznika.getObracunskiRacun());
				mt103.setSwiftKodBankePoverioca(bankaPrimaoca.getSwiftKod());
				mt103.setObracunskiRacunBankePoverioca(bankaPrimaoca.getObracunskiRacun());
				mt103.setDuznik(primljenNalog.getDuznik());
				mt103.setSvrhaPlacanja(primljenNalog.getSvrhaPlacanja());
				mt103.setPrimalac(primljenNalog.getPrimalac());
				mt103.setDatumNaloga(primljenNalog.getDatumNaloga());
				mt103.setDatumValute(primljenNalog.getDatumValute());
				mt103.setRacunDuznika(primljenNalog.getRacunDuznika());
				mt103.setModelZaduzenja(primljenNalog.getModelZaduzenja());
				mt103.setPozivNaBrojZaduzenja(primljenNalog.getPozivNaBrojZaduzenja());
				mt103.setRacunPoverioca(primljenNalog.getRacunPrimaoca());
				mt103.setModelOdobrenja(primljenNalog.getModelOdobrenja());
				mt103.setPozivNaBrojOdobrenja(primljenNalog.getPozivNaBrojOdobrenja());
				mt103.setIznos(primljenNalog.getIznos());
				mt103.setSifraValute(primljenNalog.getOznakaValute());
				
				MT103Service.save(mt103);
				racunDuznika.setRezervisano(primljenNalog.getIznos().negate());
				racunService.save(racunDuznika);
				bankaClient.sendMT103(mt103);
				
			}else{
				System.out.println("Nije hitno");
			}
			
			/*racunDuznika.setRezervisano(primljenNalog.getIznos().negate());
			racunPrimaoca.setRezervisano(primljenNalog.getIznos());
			
			
			System.out.println("Rezervisano za duznika" + racunDuznika.getRezervisano());
			
			racunService.save(racunDuznika);
			racunService.save(racunPrimaoca);*/ //trebalo bi u response u firmi da se 
												//azuriraju racuni
		}
		
		System.out.println(webServiceTemplate.getDefaultUri()); //od narodne banke
		
		//bankaClient.sendNalog();		
		return response;
	}
	
	
}