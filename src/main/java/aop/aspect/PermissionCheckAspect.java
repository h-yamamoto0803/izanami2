package aop.aspect;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import exception.InsufficientPermissionException;
import presentation.controller.pageproperty.SessionKeyword;
import presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import presentation.form.LoginUserForm;

//AOPの処理を行うクラスと設定、Springが管理できるように設定
@Aspect
@Component
public class PermissionCheckAspect {

	HttpServletRequest request;

	@Autowired
	public PermissionCheckAspect(HttpServletRequest request) {
		this.request = request;
	}

	//対象アノテーションがついてるメソッドをpermissionCheckMethod()と扱う
	@Pointcut("@annotation(aop.aspect.PermissionCheck)")
	public void permissionCheckMethod() {
	}

	@Before("permissionCheckMethod()")
	public void checkPermission() {
		// ログインユーザの取得
		LoginUserForm loginUserForm = (LoginUserForm) request.getSession().getAttribute(SessionKeyword.LOGIN_USER);

		// ログインユーザが取得できない場合、または権限がない場合はエラー画面に遷移
		if (loginUserForm == null || !hasRequiredPermission(request.getRequestURI(), loginUserForm)) {
			throw new InsufficientPermissionException("ユーザータイプが不正です\"");
		}

	}

	private boolean hasRequiredPermission(String targetURL, LoginUserForm loginUserForm) {

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

}
