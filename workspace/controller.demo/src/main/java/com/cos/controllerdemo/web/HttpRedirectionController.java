package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpRedirectionController {

	@GetMapping("/home")
	public String home() {
		
		return "home";
	}
	
	@GetMapping
	public String away() {
		
		return "redirect:/home";
	}
}