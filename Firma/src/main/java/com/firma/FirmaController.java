package com.firma;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.banka.PresekVreme;
import com.firmas.FirmaClient;
import com.firmas.Firmas;
import com.firmas.FirmasService;


@RestController
@RequestMapping("/firma")
public class FirmaController {

	int pageSize = 4;
	@Autowired
	private FirmaService firmaService;

	@Autowired
	HttpSession httpSession;
	
	@Autowired
	FirmasService firmasService;
	
	@Autowired
	FirmaClient firmaClient;
	
	
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
	
	@PostMapping("/findPresek")
	@ResponseStatus(HttpStatus.OK)
	public int findPreseke(@RequestBody PresekVreme presek) {
		
		Firmas firmas = (Firmas) httpSession.getAttribute("user");
		Firma firma = firmaService.findOne(firmas.getFirma().getId());
		firmaClient.findPreseke(presek.getStartDatum(),presek.getKrajDatum(),presek.getStranica())
		
		return 1;
	}
	
	
	
	
	
}
