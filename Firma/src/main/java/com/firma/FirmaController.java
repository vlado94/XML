package com.firma;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/firma")
public class FirmaController {

	@Autowired
	private FirmaService firmaService;

	
	@Autowired
	public FirmaController(final FirmaService firmaService){
		this.firmaService = firmaService;
	}
	
	
	@GetMapping("/findAllPoslovniSaradnici/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Firma> findAllPoslovniSaradnici(@PathVariable Long id) {
		List<Firma> saradnici = firmaService.findOne(id).getPoslovniSaradnici();
		
		return saradnici;
	}
	/*@Autowired
	FirmaClient firmClient;
	
	@PostMapping("/obrada")
	public void obradi(@RequestBody long id){
		Faktura f = fakturaService.findOne(id);
		firmClient.sendNalog(f);
	}*/
}
