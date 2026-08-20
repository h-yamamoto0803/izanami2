package com.example.demo.presentation.controller;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestMenuController {
	
	@GetMapping({INDEX_BLANK, INDEX_SLASH,MENU_HTML})
	public String GuestMenuController(Model model) {
		model.addAttribute("userType", "guest");
		return "menu";
	}
}
