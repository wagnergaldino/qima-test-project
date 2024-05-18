package br.net.galdino.qima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.net.galdino.qima.domain.Person;

@Controller
public class PersonController {
	
	@GetMapping("/")
	public String formLogin(Model model) {
		Person person = new Person();
	    model.addAttribute("person", person);
	    return "login_form";
	}

}
