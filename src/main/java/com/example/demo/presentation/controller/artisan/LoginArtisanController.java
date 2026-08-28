package com.example.demo.presentation.controller.artisan;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.service.LoginService;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.LoginUserForm;

import lombok.RequiredArgsConstructor;

/**
 * Artisan（職人）のログインに関する画面遷移を担当するControllerです。
 */
@RequiredArgsConstructor
@Controller
public class LoginArtisanController {

	private final LoginService loginService;

	/**
	 * Artisanログイン画面を表示します。
	 *
	 * @param loginUserForm Artisanログイン画面で使用するフォーム
	 * @return Artisanログイン画面
	 */
	@GetMapping(LOGIN_ARTISAN_CONTROLLER)
	public String showLoginArtisan(
			@ModelAttribute LoginUserForm loginUserForm) {

		return ARTISAN_LOGIN_HTML;
	}

	/**
	 * Artisanログイン画面からログイン処理を実行します。
	 *
	 * @param loginUserForm Artisanログイン画面から送信された入力値
	 * @param session ログイン情報を保持するセッション
	 * @return Artisanメニュー画面
	 */
	@PostMapping(LOGIN_ARTISAN_CONTROLLER)
	public String loginArtisan(
			@Validated @ModelAttribute(LOGIN_FORM) LoginUserForm loginUserForm,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		if (bindingResult.hasErrors()) {
			return ARTISAN_LOGIN_HTML;
		}

		Integer user = loginService.doLogin(loginUserForm);

		if (user != null
				&& user == 1
				&& loginUserForm.isArtisan()) {
			// ログインユーザーの情報をセッションに保存
            session.setAttribute(SessionKeyword.LOGIN_USER,loginUserForm);

			return REDIRECT_MENU;
		}

		model.addAttribute(MESSAGE_ERROR,"メールアドレスまたはパスワードが正しくありません。");

		return ARTISAN_LOGIN_HTML;
	}
}