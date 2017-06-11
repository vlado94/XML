package com.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.firmas.FirmasService;

@RestController
@RequestMapping("/user")
public class UserController {
	private HttpSession httpSession;
	private FirmasService firmasService;

	@Autowired
	public UserController(final HttpSession httpSession,FirmasService firmasService) {
		this.httpSession = httpSession;
		this.firmasService = firmasService;
	}
	
	@PostMapping(path = "/logIn")
	@ResponseStatus(HttpStatus.OK)
	public User logIn(@RequestBody User userInput) {
		User user = null;
		if (firmasService.findOneByMailAndPassword(userInput.getMail(), userInput.getPassword()) != null) {
			user = firmasService.findOneByMailAndPassword(userInput.getMail(), userInput.getPassword());
		}
		if (user != null) {
			httpSession.setAttribute("user", user);
		}
		return user;
	}

	@GetMapping(path = "/logOut")
	public void logOut() {
		httpSession.invalidate();
	}
}
