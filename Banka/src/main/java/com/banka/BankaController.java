package com.banka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.BankaClient;
import com.mt102.MT102;
import com.mt102.MT102Service;


@RestController
@RequestMapping("/banka")
public class BankaController {

	@Autowired
	BankaClient bankaClient;
	
	private final MT102Service MT102Service;
	
	@Autowired
	public BankaController(final MT102Service MT102Service){
		this.MT102Service = MT102Service;
	}
	
	@GetMapping("/obradaMT102")
	public void obradiMt102(/*@RequestBody long id*/){
		//Faktura f =/* fakturaService.findOne(id);*/ new Faktura();
		//firmClient.sendNalog(f);
		MT102  mt102 = MT102Service.findOne((long) 1);
		
		bankaClient.sendNalog(mt102);
	}
}
