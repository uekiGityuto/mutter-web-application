package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.User;
import com.example.service.ManagementService;

@Controller
@RequestMapping("management")
public class ManagementController {
	
	@Autowired
	ManagementService managementService;
			
	@GetMapping
	String moveMangement(Model model) {
		List<User> users = managementService.findAll();
		model.addAttribute("users", users);
		return "management";
	}

	@PostMapping(path = "enable")
	String enableUser(@RequestParam Integer id) {
		managementService.enable(id);
		return "redirect:/management";
	}
	
	@PostMapping(path = "disable")
	String disableUser(@RequestParam Integer id) {
		managementService.disable(id);
		return "redirect:/management";
	}

	@PostMapping(path = "delete")
	String deleteUser(@RequestParam Integer id) {
		managementService.delete(id);
		return "redirect:/management";
	}

}
