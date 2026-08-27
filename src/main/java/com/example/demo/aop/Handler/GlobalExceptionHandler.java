package com.example.demo.aop.Handler;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.MessageForm;

/*
 * ほぼEMからの流用、エラーの記録方法についてはEMそのままなので、必要に応じて変更したほうがいいかも
 * LOGIN_HTML側に表示用の要素を追加してください
 * 以下のHTMLは一例
 * 
  	<p style="color: red;"
	   th:if="${messageError != null}"
	   th:text="${messageError.message}">
	</p>
 */

/**
 * 例外処理用のコントローラ
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

	private final String SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION = "不正な遷移です。";
	private final String SEVERE_ERROR_MESSAGE_BAD_REQUEST = "不正なリクエストです。";

	/**
	 * 権限がない場合の例外処理
	 *
	 * @param model ビューにデータを渡すためのコンテナ
	 * @return 遷移先の画面
	 */
	@ExceptionHandler(InsufficientPermissionException.class)
	public String handleInsufficientPermissionException(Model model, Exception e, HttpSession session) {

		// セッション情報破棄
		session.invalidate();
		// エラーメッセージとログインページ用のフォームをセット
		model.addAttribute(MESSAGE_ERROR, new MessageForm(SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION));
		model.addAttribute(LOGIN_USER_FORM, new LoginUserForm());

		// エラーをコンソールに出力
		e.printStackTrace();

		// エラーログ出力
		LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION, e);

		return TransitionTargetPageNameKeyword.LOGIN_HTML;

	}

	/**
	 * リクエストメソッドが不正な場合の例外処理
	 *
	 * @param model ビューにデータを渡すためのコンテナ
	 * @return ログイン画面
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String handleBadRequestException(Model model, Exception e, HttpSession session) {

		session.invalidate();
		model.addAttribute(MESSAGE_ERROR, SEVERE_ERROR_MESSAGE_BAD_REQUEST);
		model.addAttribute(LOGIN_USER_FORM, new LoginUserForm());

		e.printStackTrace();

		LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION, e);

		return TransitionTargetPageNameKeyword.LOGIN_HTML;
	}

	/**
	 * その他の例外処理
	 *
	 * @param model ビューにデータを渡すためのコンテナ
	 * @return ログイン画面
	 */
	@ExceptionHandler(Exception.class)
	public String AllException(Model model, Exception e, HttpSession session) {

		session.invalidate();
		model.addAttribute("error", SEVERE_ERROR_MESSAGE_BAD_REQUEST);
		model.addAttribute(LOGIN_USER_FORM, new LoginUserForm());

		e.printStackTrace();

		LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION, e);

		return TransitionTargetPageNameKeyword.LOGIN_HTML;
	}
}
