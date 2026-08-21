package com.example.demo.presentation.controller.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

@Controller
public class InsertPostConfirmController {
	
	/*
	 * 投稿確認画面表示
	 */
	@GetMapping(TransitionTargetPageNameKeyword.INSERT_POST_CONFIRM)
	public String confirmPost() {
		return TransitionTargetPageNameKeyword.POST_CONFIRM_HTML;
	}
	
	
}
