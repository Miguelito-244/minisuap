package br.edu.ifpb.academico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class PaginaPricipal {
	
	@GetMapping("")
	public String paginaPrincipal(Model model) {
		return "paginaPrincipal";
	}
 //aiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
}
