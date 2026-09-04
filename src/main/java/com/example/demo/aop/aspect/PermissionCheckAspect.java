package com.example.demo.aop.aspect;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;

//AOPの処理を行うクラスと設定、Springが管理できるように設定
@Aspect
@Component
public class PermissionCheckAspect {

	HttpServletRequest request;

	
	public PermissionCheckAspect(HttpServletRequest request) {
		this.request = request;
	}

	//対象アノテーションがついてるメソッドをpermissionCheckMethod()と扱う
	@Pointcut("@annotation(com.example.demo.aop.aspect.PermissionCheck)")
	public void permissionCheckMethod() {
	}

	@Before("permissionCheckMethod()")
	public void checkPermission() {
		// ログインユーザの取得
		LoginUserForm loginUserForm = (LoginUserForm) request.getSession().getAttribute(SessionKeyword.LOGIN_USER);
		
	  
		// ログインユーザが取得できない場合、または権限がない場合はエラー画面に遷移
		if (!hasRequiredPermission(request.getRequestURI(), loginUserForm)) {
			throw new InsufficientPermissionException("ユーザータイプが不正です");
		}

	}

	private boolean hasRequiredPermission(String targetURL, LoginUserForm loginUserForm) {

		// ゲスト且つゲストページリストに含まれるリクエスト
		if (isGuestTransition(targetURL, loginUserForm)) {
			return true;
		}

		// ゲスト且つゲストページリストに含まれるリクエストではない
		if (loginUserForm == null) {
			return false;
		}

		// 職人
		if (isArtisanTransition(targetURL, loginUserForm)) {
			return true;
		}

		// 消費者
		if (isCustomerTransition(targetURL, loginUserForm)) {
			return true;
		}

		// アクセス不許可の場合の処理（不正リクエスト画面に遷移）
		return false;

	}

	private boolean isArtisanTransition(String targetURL, LoginUserForm loginUserForm) {

		// 職人ではない場合false
		if (!loginUserForm.isArtisan()) {
			return false;
		}

		// 職人向けのリクエストでない場合false
		if (!TransitionTargetPageNameKeyword.getArtisanPageList().contains(targetURL)) {
			return false;
		}

		return true;
	}

	private boolean isCustomerTransition(String targetURL, LoginUserForm loginUserForm) {

		// 消費者ではない場合false
		if (!loginUserForm.isCustomer()) {
			return false;
		}

		// 消費者向けのリクエストでない場合false
		if (!TransitionTargetPageNameKeyword.getCustomerPageList().contains(targetURL)) {
			return false;
		}

		return true;

	}

	private boolean isGuestTransition(String targetURL, LoginUserForm loginUserForm) {

		// ゲストではない場合false
		if (loginUserForm != null) {
			return false;
		}

		// ゲスト向けのリクエストでない場合false
		if (!TransitionTargetPageNameKeyword.getGuestPageList().contains(targetURL)) {
			return false;
		}

		return true;

	}

}
