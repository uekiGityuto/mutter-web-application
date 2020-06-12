package com.example.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.service.LoginUserDetails;
import com.example.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired UserService userService;
	
	@ModelAttribute
	UserForm setUpForm() {
		return new UserForm();
	}	
	
	@GetMapping(path = "registration")
	String moveRegisterView(Model model) {
		return "registration";
	}
	
	//登録処理
	@PostMapping(path = "registerResult")
	String registerUser(@Validated UserForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return moveRegisterView(model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		try {
			userService.create(user);
		} catch(DataAccessException e) {
			FieldError fieldError = new FieldError(result.getObjectName(), "name", "既に使用されています。別のUsernameを使用してください");
			result.addError(fieldError);
			return moveRegisterView(model);
		}
		return "registerResult";
	}
	
	//退会処理
	@PostMapping(path = "withdrawResult")
	String deleteUser(Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
		userService.delete(userDetails.getUser());
		return "withdrawResult";
	}

	//TOPページに戻る処理
	@GetMapping(path = "gotoTop")
	String gotoTop(Model model) {
		return "redirect:/index";
	}

	
	

}
