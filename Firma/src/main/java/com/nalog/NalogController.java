package com.nalog;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.faktura.Faktura;
import com.firma.Firma;
import com.firma.FirmaService;
import com.firmas.FirmaClient;


@RestController
@RequestMapping("/nalog")
public class NalogController {

	private final FirmaService firmaService;
	private HttpSession httpSession;
	private final NalogService nalogService;
	
	@Autowired
	FirmaClient firmClient;
	
	
	@Autowired
	public NalogController(final HttpSession httpSession,final FirmaService firmaService,final NalogService nalogService) {
		this.httpSession = httpSession;
		this.firmaService = firmaService;
		this.nalogService = nalogService;
	}
	
	
	///ovako kreirani nalog bi trebao da se posalje banci preko SOAP-a
		//ili kao xml nalog ili obicni objekat
		@PostMapping(path = "/sendNalog")
		@ResponseStatus(HttpStatus.CREATED)
		public Nalog sendNalog(@RequestBody Faktura faktura){
			System.out.println("sendNalog "+faktura.getZaglavljeFakture().getNazivKupca());
			Nalog nalog = new Nalog();
			String uniqueID = UUID.randomUUID().toString();
			System.out.println(uniqueID);
			nalog.setIdPoruke(uniqueID);
			nalog.setDuznik(faktura.getZaglavljeFakture().getNazivKupca());
			nalog.setSvrhaPlacanja("prodaja");
			nalog.setPrimalac(faktura.getZaglavljeFakture().getNazivDobavljaca());
			nalog.setDatumNaloga(faktura.getZaglavljeFakture().getDatumRacuna());
			nalog.setDatumValute(faktura.getZaglavljeFakture().getDatumValute());
			Firma duznik = firmaService.findByPIB(faktura.getZaglavljeFakture().getPibKupca());
			System.out.println(duznik.getRacun().getBrojRacuna());
			nalog.setRacunDuznika(duznik.getRacun().getBrojRacuna());
			nalog.setModelZaduzenja(BigInteger.valueOf(97L));
			nalog.setPozivNaBrojZaduzenja("11111111111111111111");
			nalog.setRacunPrimaoca(faktura.getZaglavljeFakture().getUplataNaRacun());
			nalog.setModelOdobrenja(BigInteger.valueOf(97L));
			nalog.setPozivNaBrojOdobrenja("22222222222222222222");
			nalog.setIznos(faktura.getZaglavljeFakture().getIznosZaUplatu());
			nalog.setOznakaValute(faktura.getZaglavljeFakture().getOznakaValute());
			//ako je vece od 250000 postavi da je nalog hitan
			if(nalog.getIznos().compareTo(BigDecimal.valueOf(250000L))>1){
				nalog.setHitno(true);
			}else{
				nalog.setHitno(false);
			}
			
			firmClient.sendNalogTemp(nalog);
			return nalogService.save(nalog);
			
			
		}
}
