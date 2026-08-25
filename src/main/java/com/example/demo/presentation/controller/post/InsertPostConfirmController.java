package com.example.demo.presentation.controller.post;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.service.post.InsertPost;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.post.InsertPostForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InsertPostConfirmController {
	
	private final InsertPost insertPost;
	private final HttpSession httpSession;
	
	/*
	 * 投稿確認画面表示
	 * @return 投稿確認画面
	 */
	@PostMapping(TransitionTargetPageNameKeyword.INSERT_POST_CONFIRM)
	public String confirmPost(@ModelAttribute InsertPostForm insertPostForm) {
		return TransitionTargetPageNameKeyword.POST_CONFIRM_HTML;
	}
	
	/*
	 * 投稿処理
	 * @return メニュー画面
	 * 
	 */
	@PostMapping(TransitionTargetPageNameKeyword.POST)
	public String post(@ModelAttribute InsertPostForm insertPostForm) {
		
//		セッションからユーザーIDを取得
		Integer userId = (Integer) httpSession.getAttribute("userId");
//		サービスの呼び出し
		insertPost.insertPost(insertPostForm, userId);
		
		return TransitionTargetPageNameKeyword.REDIRECT
       		 +TransitionTargetPageNameKeyword.RETURN_MENU;
}
	
}
