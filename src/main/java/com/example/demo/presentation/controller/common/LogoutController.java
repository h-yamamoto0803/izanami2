package com.example.demo.presentation.controller.common;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

/**
 * ログアウトに関する処理を担当するControllerです。
 *
 * ログアウトボタンが押された際に呼び出され、
 * 共通メニュー画面へ遷移します。
 */
@Controller
public class LogoutController {
	
	@PermissionCheck
	@PostMapping(TransitionTargetPageNameKeyword.LOGOUT_CONTROLLER)
	public String logout(HttpSession session) {

	    session.invalidate();

	    return TransitionTargetPageNameKeyword.REDIRECT_MENU;
	}
}