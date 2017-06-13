package com.banka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.BankaClient;
import com.mt102.MT102;
import com.mt102.MT102Service;
import com.mt103.MT103;
import com.mt103.MT103Service;


@RestController
@RequestMapping("/banka")
public class BankaController {

	@Autowired
	BankaClient bankaClient;
	
	private final MT102Service MT102Service;
	private final MT103Service MT103Service;
	
	@Autowired
	public BankaController(final MT102Service MT102Service,final MT103Service MT103Service){
		this.MT102Service = MT102Service;
		this.MT103Service = MT103Service;
	}
	
	@GetMapping("/obradaMT102")
	public void obradiMt102(/*@RequestBody long id*/){
		//Faktura f =/* fakturaService.findOne(id);*/ new Faktura();
		//firmClient.sendNalog(f);
		MT102  mt102 = MT102Service.findOne((long) 1);
		
		bankaClient.sendMT102(mt102);
	}
	
	@GetMapping("/obradaMT103")
	public void obradiMT103(/*@RequestBody long id*/){
		//Faktura f =/* fakturaService.findOne(id);*/ new Faktura();
		//firmClient.sendNalog(f);
		///MT103  mt102 = MT102Service.findOne((long) 1);
		MT103 mt103 = MT103Service.findOne((long)1);
		bankaClient.sendMT103(mt103);
	}
}
