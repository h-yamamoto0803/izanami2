package com.example.demo.presentation.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

/**
 * ログアウトに関する処理を担当するControllerです。
 *
 * ログアウトボタンが押された際に呼び出され、
 * 共通メニュー画面へ遷移します。
 */
@Controller
public class LogoutController {

	@PostMapping(TransitionTargetPageNameKeyword.LOGOUT_CONTROLLER)
	public String logout(HttpSession session) {

	    session.invalidate();

	    return "redirect:" + TransitionTargetPageNameKeyword.RETURN_MENU;
	}
}