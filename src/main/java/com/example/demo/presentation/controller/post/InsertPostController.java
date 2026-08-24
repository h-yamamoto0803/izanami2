package com.example.demo.presentation.controller.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.service.post.SearchTags;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.post.InsertPostForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InsertPostController {
	
	private final SearchTags service;
	/*
	 * 投稿作成画面表示
	 */
	@GetMapping(TransitionTargetPageNameKeyword.INSERT_POST)
	public String insertPostGet(@ModelAttribute InsertPostForm insertPostForm) {
		
		insertPostForm.setTags(service.getTags());
		
		return TransitionTargetPageNameKeyword.POST_CREATE_HTML;
	}
	@PostMapping(TransitionTargetPageNameKeyword.INSERT_POST)
	public String insertPost(@ModelAttribute InsertPostForm insertPostForm,
			Model model) {
		model.addAttribute("insertPostForm",insertPostForm);
		
		return TransitionTargetPageNameKeyword.POST_CREATE_HTML;
	}
	
	
	
	
}
