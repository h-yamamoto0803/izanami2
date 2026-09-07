package com.example.demo.aop.Handler;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.common.MessageForm;

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
	public String handleInsufficientPermissionException(
	        RedirectAttributes redirectAttributes,
	        InsufficientPermissionException e,
	        HttpSession session) {

	    session.invalidate();

	    redirectAttributes.addFlashAttribute(
	            MESSAGE_ERROR,
	            new MessageForm(SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION));

	    e.printStackTrace();
	    LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_ILLEGAL_TRANSITION, e);

	    return TransitionTargetPageNameKeyword.REDIRECT_MENU;
	}

	/**
	 * リクエストメソッドが不正な場合の例外処理
	 *
	 * @param model ビューにデータを渡すためのコンテナ
	 * @return ログイン画面
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String handleBadRequestException(
	        RedirectAttributes redirectAttributes,
	        Exception e,
	        HttpSession session) {

	    session.invalidate();

	    redirectAttributes.addFlashAttribute(
	            MESSAGE_ERROR,
	            new MessageForm(SEVERE_ERROR_MESSAGE_BAD_REQUEST));

	    e.printStackTrace();
	    LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_BAD_REQUEST, e);

	    return TransitionTargetPageNameKeyword.REDIRECT_MENU;
	}

	/**
	 * 静的リソースが見つからない場合の例外処理、ログ出力のみを行い、セッション破棄などはしない
	 * @param e
	 * @return 無し
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleNoResourceFoundException(NoResourceFoundException e) {

		LOGGER.log(Level.WARNING, "静的リソースが見つかりません。", e);

	}

	/**
	 * その他の例外処理
	 *
	 * @param model ビューにデータを渡すためのコンテナ
	 * @return ログイン画面
	 */
	@ExceptionHandler(Exception.class)
	public String AllException(
	        RedirectAttributes redirectAttributes,
	        Exception e,
	        HttpSession session) {

	    session.invalidate();

	    redirectAttributes.addFlashAttribute(
	            MESSAGE_ERROR,
	            new MessageForm(SEVERE_ERROR_MESSAGE_BAD_REQUEST));

	    e.printStackTrace();
	    LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE_BAD_REQUEST, e);

	    return TransitionTargetPageNameKeyword.REDIRECT_MENU;
	}
}
