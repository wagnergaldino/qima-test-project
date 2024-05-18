package br.net.galdino.qima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.net.galdino.qima.domain.Person;
import br.net.galdino.qima.repository.PersonRepository;
import jakarta.validation.Valid;

@Controller
public class PersonController {
	
	private PersonRepository personRepository;
	
	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@GetMapping("/")
	public String formLogin(Model model) {
		Person person = new Person();
	    model.addAttribute("person", person);
	    return "login_form";
	}
	
	@PostMapping("/login")
	public String checkPersonInfo(@Valid Person person, BindingResult bindingResult, Model model) {
		
		try {

			if (person.getEmail() == null || person.getPwd() == null || person.getEmail().isEmpty() || person.getPwd().isEmpty()) {
				return "login_form";
			}
			
			if (bindingResult.hasErrors()) {
				return "login_form";
			}

			Person p = personRepository.findByEmailAndPwd(person.getEmail(), person.getPwd());
			
			if (p == null) {
				model.addAttribute("loginError", true);
				return "login_form";
			}
			
		} catch (Exception e) {
			model.addAttribute("loginError", true);
		}

		return "redirect:/products";
		
	}

}
