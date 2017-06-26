package com.firmas;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.firma.Firma;
import com.firma.FirmaClient;

@RestController
@RequestMapping("/firmas")
public class FirmasController {
	private final FirmasService firmasService;
	private HttpSession httpSession;
	
	@Autowired
	FirmaClient firmClient;
	
	@Autowired
	Environment environment;

	
	@Autowired
	public FirmasController(final HttpSession httpSession,final FirmasService firmasService) {
		this.httpSession = httpSession;
		this.firmasService = firmasService;
	}
	
	@GetMapping("/checkRights")
	@ResponseStatus(HttpStatus.OK)
	public Firmas checkRights() throws AuthenticationException {
		try {
			Firmas admin = firmasService.findOneById(((Firmas) httpSession.getAttribute("user")).getId());
			Firma firma = admin.getFirma();

			
			String port = environment.getProperty("local.server.port");
			
			String[] splitovano = firma.getUri().split(":");
			
			if(!port.equals(splitovano[2])){
				return null;
			}else{
			
			//firmClient.sendNalogTemp();
			
			return admin;
			}
		} catch (Exception e) {
			throw new AuthenticationException("Forbidden.");
		}
	}
}
