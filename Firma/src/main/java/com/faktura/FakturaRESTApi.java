package com.faktura;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/RESTApi")
public class FakturaRESTApi {
	
	private final FakturaService fakturaService;

	@Autowired
	public FakturaRESTApi(final FakturaService fakturaService){
		this.fakturaService = fakturaService;
	}

	@RequestMapping(value = "/FIRMA_A", method = RequestMethod.POST)
	public ResponseEntity<Faktura> acceptFaktura1(@RequestBody Faktura faktura) 
	{
		System.out.println("FakturaRESTApi FIRMA_A");
	    System.out.println(faktura);
	    Faktura savedFaktura = fakturaService.save(faktura);
	    return new ResponseEntity<Faktura>(savedFaktura, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/FIRMA_B", method = RequestMethod.POST)
	public ResponseEntity<Faktura> acceptFaktura2(@RequestBody Faktura faktura) 
	{
		System.out.println("FakturaRESTApi FIRMA_B");
	    System.out.println(faktura);
	    Faktura savedFaktura = fakturaService.save(faktura);
	    return new ResponseEntity<Faktura>(savedFaktura, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/FIRMA_C", method = RequestMethod.POST)
	public ResponseEntity<Faktura> acceptFaktura3(@RequestBody Faktura faktura) 
	{
		System.out.println("FakturaRESTApi FIRMA_C");
	    System.out.println(faktura);
	    Faktura savedFaktura = fakturaService.save(faktura);
	    return new ResponseEntity<Faktura>(savedFaktura, HttpStatus.OK);
	}
	
}
