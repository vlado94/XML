package com.banka;

import java.math.MathContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.BankaClient;
import com.mt102.MT102;
import com.mt102.MT102Service;
import com.mt102.PojedinacnoPlacanjeMT102;
import com.mt103.MT103Service;
import com.mt900.MT900;
import com.racun.Racun;
import com.racun.RacunService;


@RestController
@RequestMapping("/banka")
public class BankaController {

	@Autowired
	BankaClient bankaClient;
	
	@Autowired
	RacunService racunService;
	
	private final MT102Service MT102Service;
	private final MT103Service MT103Service;
	
	
	@Autowired
	public BankaController(final MT102Service MT102Service,final MT103Service MT103Service){
		this.MT102Service = MT102Service;
		this.MT103Service = MT103Service;
	}
	
	@GetMapping("/posaljiMT102")
	public void obradiMt102(){
		
		MT102  mt102 = MT102Service.findOne((long) 1); //ovdje treba staviti for gdje prolazi kroz sve 
			//mt102 poruke i svaku posebno salje
		
		MT900 mt900 = bankaClient.sendMT102(mt102);
		
		//kada se obradi mt102 potrebno je sa svakog racuna duznika skinuti novac
		for(int i = 0; i< mt102.getPojedinacnoPlacanjeMT102().size(); i++){
			PojedinacnoPlacanjeMT102 placanje = mt102.getPojedinacnoPlacanjeMT102().get(i);
			
			String brojRacunaDuznika = placanje.getRacunDuznika();
			Racun racunDuznika = racunService.findByBrojRacuna(brojRacunaDuznika);
			
			MathContext mc = new MathContext(2);
			racunDuznika.setTrenutnoStanje(racunDuznika.getTrenutnoStanje().subtract(racunDuznika.getRezervisano().subtract(placanje.getIznos()),mc) );
			
			racunDuznika.setRezervisano(racunDuznika.getRezervisano().subtract(placanje.getIznos()));
			racunService.save(racunDuznika);
		}
		
		mt102.setStatus(true); //mt102 postaje obradjen
		MT102Service.save(mt102);
		System.out.println("Obradjena sva pojedinacna placanja");
		
		
	}
	
	
}
