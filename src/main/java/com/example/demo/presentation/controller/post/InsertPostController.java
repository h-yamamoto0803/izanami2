package com.example.demo.presentation.controller.post;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.post.InsertPostForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InsertPostController {

	/*
	 * 投稿作成画面表示
	 */
	@PermissionCheck
	@GetMapping(INSERT_POST)
	public String insertPost(@ModelAttribute InsertPostForm insertPostForm,
			RedirectAttributes redirect,
			HttpSession session) {

		// ログインしたユーザーか判定
		LoginUserForm loginUserForm = (LoginUserForm)session.getAttribute(LOGIN_USER);
		if (loginUserForm == null) {
			redirect.addFlashAttribute(INSERT_POST_MESSAGE, "投稿するにはログインしてください。");
			return REDIRECT_MENU;
		}

		return POST_CREATE_HTML;
	}

	/*
	 * 投稿確認画面の戻るボタン
	 * POSTされた入力中の値がformに入ったまま
	 * @return 投稿作成画面
	 */
	@PermissionCheck
	@PostMapping(INSERT_POST_RET)
	public String insertPostRet(InsertPostForm insertPostForm) {
		return POST_CREATE_HTML;
	}
}
