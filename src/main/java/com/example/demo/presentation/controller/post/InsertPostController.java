package com.example.demo.presentation.controller.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

@Controller
public class InsertPostController {
	
	/*
	 * 投稿作成画面表示
	 */
	@GetMapping(TransitionTargetPageNameKeyword.INSERT_POST)
	public String insertPost() {
		return TransitionTargetPageNameKeyword.POST_CREATE_HTML;
	}
	
	/*
	 * 投稿作成画面の戻るボタン
	 * @return メニュー画面
	 */
	@GetMapping(TransitionTargetPageNameKeyword.RETURN_MENU)
	public String returnMenu() {
		return TransitionTargetPageNameKeyword.MENU_HTML;
	}
	
	
}
