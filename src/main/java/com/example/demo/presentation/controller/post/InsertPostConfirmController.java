package com.example.demo.presentation.controller.post;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.post.InsertPostService;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.post.InsertPostForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InsertPostConfirmController {
	
	private final InsertPostService insertPostService;
	private final HttpSession httpSession;
	/*
	 * 投稿確認画面表示
	 * @return 投稿確認画面
	 */
	@PermissionCheck
	@PostMapping(INSERT_POST_CONFIRM)
	public String confirmPost(@ModelAttribute InsertPostForm insertPostForm){
		return POST_CONFIRM_HTML;
	}
	
	/*
	 * 投稿処理
	 * @return メニュー画面
	 * 
	 */
	@PermissionCheck
	@PostMapping(DO_INSERT_POST)
	public String post(@ModelAttribute InsertPostForm insertPostForm){
		
		//セッションからユーザーIDを取得
		
		LoginUserForm loginUserForm =  (LoginUserForm)httpSession.getAttribute("loginUser");
		Integer userId = loginUserForm.getUserId();
		//サービスの呼び出し
		insertPostService.insertPost(insertPostForm, userId);
		
		return REDIRECT_MENU;
	}
}