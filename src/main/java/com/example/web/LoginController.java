package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class LoginController {

	@ModelAttribute
	MutterForm setUpForm() {
		return new MutterForm();
	}

	@GetMapping
	String gotoIndex(Model model) {
		return "index";
	}
}
