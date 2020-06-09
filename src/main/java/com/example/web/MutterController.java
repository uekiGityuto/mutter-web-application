package com.example.web;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	String displayMutters(Model model) {
		List<Mutter> mutters = mutterService.findAll();
		model.addAttribute("mutters", mutters);
		return "main";
	}
/*	
	@PostMapping
	String createMutter(@Validated MutterForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails) {
		if(result.hasErrors()) {
			return displayMutters(model);
		}
		Mutter mutter = new Mutter();
		BeanUtils.copyProperties(form, mutter);
		mutterService.create(mutter, userDetails.getUser());
		return "redirect:/main";
	}
*/
	@PostMapping
	String createMutter(@Validated MutterForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails) {
		if(result.hasErrors()) {
			return displayMutters(model);
		}
		Mutter mutter = new Mutter();
		BeanUtils.copyProperties(form, mutter);
		//mutter.setUserName(userDetails.getUser().getName());
		//mutter.setText(form.getText());
		mutterService.create(mutter, userDetails.getUser());
		return "redirect:/main";
	}

}
