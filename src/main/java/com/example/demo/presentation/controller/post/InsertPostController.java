package com.example.demo.presentation.controller.post;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.post.InsertPostForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InsertPostController {
	
	// private final SearchTags service;
	private final HttpSession httpSession;
	
	/*
	 * 投稿作成画面表示
	 */
	@GetMapping(TransitionTargetPageNameKeyword.INSERT_POST)
	public String insertPost(@ModelAttribute InsertPostForm insertPostForm) {
		
		// insertPostForm.setTags(service.getTags());
		
		return TransitionTargetPageNameKeyword.POST_CREATE_HTML;
	}

}
