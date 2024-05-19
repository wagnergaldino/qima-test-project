package br.net.galdino.qima.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.net.galdino.qima.domain.Person;
import br.net.galdino.qima.repository.PersonRepository;

public class PersonControllerTest {
	
	@Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testFormLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("person"))
                .andExpect(view().name("login_form"));
    }

    @Test
    public void testCheckPersonInfo_ValidPerson() throws Exception {
        Person person = new Person();
        person.setEmail("test@example.com");
        person.setPwd("password");

        when(personRepository.findByEmailAndPwd(person.getEmail(), person.getPwd())).thenReturn(person);

        mockMvc.perform(post("/login")
                .param("email", person.getEmail())
                .param("pwd", person.getPwd()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    public void testCheckPersonInfo_InvalidPerson() throws Exception {
        Person person = new Person();
        person.setEmail("");
        person.setPwd("");

        mockMvc.perform(post("/login")
                .param("email", person.getEmail())
                .param("pwd", person.getPwd()))
                .andExpect(status().isOk())
                .andExpect(view().name("login_form"));
    }

    @Test
    public void testCheckPersonInfo_PersonNotFound() throws Exception {
        Person person = new Person();
        person.setEmail("test@example.com");
        person.setPwd("password");

        when(personRepository.findByEmailAndPwd(person.getEmail(), person.getPwd())).thenReturn(null);

        mockMvc.perform(post("/login")
                .param("email", person.getEmail())
                .param("pwd", person.getPwd()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginError"))
                .andExpect(view().name("login_form"));
    }

}
