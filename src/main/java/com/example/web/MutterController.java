package com.example.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Mutter;
import com.example.service.LoginUserDetails;
import com.example.service.MutterService;

@Controller
@RequestMapping("main")
public class MutterController {
	@Autowired MutterService mutterService;
	
	@ModelAttribute
	MutterForm setUpForm() {
		return new MutterForm();
	}

	@GetMapping
	String displayMutters(@PageableDefault(page = 0, size = 5) Pageable pageable, Model model) {
		Page<Mutter> mutterPage = mutterService.findAll(pageable);
		model.addAttribute("page", mutterPage);
        model.addAttribute("mutters", mutterPage.getContent());
		return "main";
	}

	@PostMapping
	String createMutter(@Validated MutterForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails, Pageable pageable) {
		if(result.hasErrors()) {
			return displayMutters(pageable, model);
		}
		Mutter mutter = new Mutter();
		BeanUtils.copyProperties(form, mutter);
		mutterService.create(mutter, userDetails.getUser());
		return "redirect:/main";
	}

}
