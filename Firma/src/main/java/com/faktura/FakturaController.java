package com.faktura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firmas.FirmaClient;

@RestController
@RequestMapping("/faktura")
public class FakturaController {

	@Autowired
	private FakturaService fakturaService;

	@Autowired
	FirmaClient firmClient;
	
	@PostMapping("/obrada")
	public void obradi(@RequestBody long id){
		Faktura f = fakturaService.findOne(id);
		firmClient.sendNalog(f);
	}

	
	
	
}
