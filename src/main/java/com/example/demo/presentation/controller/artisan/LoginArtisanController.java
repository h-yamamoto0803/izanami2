package com.example.demo.presentation.controller.artisan;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.service.LoginService;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
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
	@GetMapping(TransitionTargetPageNameKeyword.LOGIN_ARTISAN_CONTROLLER)
	public String showLoginArtisan(
			@ModelAttribute(TransitionTargetPageNameKeyword.LOGIN_FORM) LoginUserForm loginUserForm) {

		return TransitionTargetPageNameKeyword.ARTISAN_LOGIN_HTML;
	}

	/**
	 * Artisanログイン画面からログイン処理を実行します。
	 *
	 * @param loginUserForm Artisanログイン画面から送信された入力値
	 * @param session ログイン情報を保持するセッション
	 * @return Artisanメニュー画面
	 */
	@PostMapping(TransitionTargetPageNameKeyword.LOGIN_ARTISAN_CONTROLLER)
	public String loginArtisan(
			@Validated @ModelAttribute(TransitionTargetPageNameKeyword.LOGIN_FORM) LoginUserForm loginUserForm,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		if (bindingResult.hasErrors()) {
			return TransitionTargetPageNameKeyword.ARTISAN_LOGIN_HTML;
		}

		Integer user = loginService.doLogin(loginUserForm);

		if (user != null
				&& user == 1
				&& loginUserForm.isArtisan()) {

			session.setAttribute("userId", loginUserForm.getUserId());
			session.setAttribute("userType", loginUserForm.getUserType());
			session.setAttribute("userName", loginUserForm.getUserName());

			return TransitionTargetPageNameKeyword.REDIRECT
					+ TransitionTargetPageNameKeyword.RETURN_MENU;
		}

		model.addAttribute(
				"errorMessage",
				"メールアドレスまたはパスワードが正しくありません。");

		return TransitionTargetPageNameKeyword.ARTISAN_LOGIN_HTML;
	}
}