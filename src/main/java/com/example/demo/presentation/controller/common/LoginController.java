
package com.example.demo.presentation.controller.common;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.LoginService;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.form.common.LoginUserForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {

	private final LoginService loginService;

	/**
	 * Artisanログイン画面を表示します。
	 *
	 * @param loginUserForm ログイン画面で使用するフォーム
	 * @return Artisanログイン画面
	 */
	@PermissionCheck
	@GetMapping(LOGIN_ARTISAN_CONTROLLER)
	public String showLoginArtisan(
			@ModelAttribute(LOGIN_FORM) LoginUserForm loginUserForm) {

		return showLoginPage(ARTISAN_LOGIN_HTML);
	}

	/**
	 * Customerログイン画面を表示します。
	 *
	 * @param loginUserForm ログイン画面で使用するフォーム
	 * @return Customerログイン画面
	 */
	@PermissionCheck
	@GetMapping(LOGIN_CUSTOMER_CONTROLLER)
	public String showLoginCustomer(
			@ModelAttribute(LOGIN_FORM) LoginUserForm loginUserForm) {

		return showLoginPage(CUSTOMER_LOGIN_HTML);
	}

	/**
	 * Artisanログイン処理を実行します。
	 *
	 * @param loginUserForm ログイン画面から送信された入力値
	 * @param bindingResult バリデーション結果
	 * @param session ログイン情報を保持するセッション
	 * @param model エラーメッセージを画面に渡すModel
	 * @return ログイン後の画面
	 */
	@PermissionCheck
	@PostMapping(LOGIN_ARTISAN_CONTROLLER)
	public String loginArtisan(
			@Validated @ModelAttribute(LOGIN_FORM) LoginUserForm loginUserForm,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		return loginUser(
				loginUserForm,
				bindingResult,
				session,
				model,
				ARTISAN_LOGIN_HTML,
				UserEntity.ARTISAN);
	}

	/**
	 * Customerログイン処理を実行します。
	 *
	 * @param loginUserForm ログイン画面から送信された入力値
	 * @param bindingResult バリデーション結果
	 * @param session ログイン情報を保持するセッション
	 * @param model エラーメッセージを画面に渡すModel
	 * @return ログイン後の画面
	 */
	@PermissionCheck
	@PostMapping(LOGIN_CUSTOMER_CONTROLLER)
	public String loginCustomer(
			@Validated @ModelAttribute(LOGIN_FORM) LoginUserForm loginUserForm,
			BindingResult bindingResult,
			HttpSession session,
			Model model) {

		return loginUser(
				loginUserForm,
				bindingResult,
				session,
				model,
				CUSTOMER_LOGIN_HTML,
				UserEntity.CUSTOMER);
	}

	/**
	 * ログイン画面を表示します。
	 *
	 * @param pageName ログイン画面名
	 * @return ログイン画面
	 */
	private String showLoginPage(String pageName) {
		return pageName;
	}

	/**
	 * ログイン処理を共通化します。
	 *
	 * @param loginUserForm ログイン情報
	 * @param bindingResult バリデーション結果
	 * @param session ログイン情報を保持するセッション
	 * @param model エラーメッセージを画面に渡すModel
	 * @param loginPageName ログイン画面名
	 * @param isArtisan Artisanログインの場合true
	 * @return ログイン後の画面
	 */
	private String loginUser(
			LoginUserForm loginUserForm,
			BindingResult bindingResult,
			HttpSession session,
			Model model,
			String loginPageName,
			Byte userType) {

		// 入力値のバリデーションエラー
		if (bindingResult.hasErrors()) {
			return loginPageName;
		}

		boolean loginSuccess = loginService.doLogin(loginUserForm);

		if (loginSuccess
				&& loginUserForm.getUserType().equals(userType)) {

			session.setAttribute(LOGIN_USER, loginUserForm);
			return REDIRECT_MENU;
		}
		// ログイン失敗
		model.addAttribute(
				MESSAGE_ERROR,
				"メールアドレスまたはパスワードが正しくありません。");

		return loginPageName;
	}
}

