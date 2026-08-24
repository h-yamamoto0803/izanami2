package com.example.demo.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.MenuService;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping(TransitionTargetPageNameKeyword.MENU)
    public String showMenu(
    @RequestParam(required = false) String genre,
    Model model) {
    model.addAttribute("posts", menuService.getPostListByGenre(genre));
    model.addAttribute("userType", "guest");
    model.addAttribute("selectedGenre", genre);
        return "menu";
    }
}