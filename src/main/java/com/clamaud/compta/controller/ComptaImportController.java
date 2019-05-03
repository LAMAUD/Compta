package com.clamaud.compta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComptaImportController {
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

}
