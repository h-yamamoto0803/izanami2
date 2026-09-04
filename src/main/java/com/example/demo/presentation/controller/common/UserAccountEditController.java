package com.example.demo.presentation.controller.common;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.common.UserAccountEditForm;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class UserAccountEditController {

	
	private final SearchCustomer searchCustomer;


	@PermissionCheck
	@GetMapping(TransitionTargetPageNameKeyword.ACCOUNT_EDIT)
	public String userAccountEdit(Model model,
			@ModelAttribute UserAccountEditForm userAccountEditForm,
			HttpSession session) {

		try {
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
			Integer userId = loginUser.getUserId();

			userAccountEditForm = searchCustomer.searchIdCustomer(userId);
			model.addAttribute(PageReturnAttributeKeyword.USER_ACCOUNT_EDIT_FORM, userAccountEditForm);
			return TransitionTargetPageNameKeyword.ACCOUNT_EDIT_HTML;
		} catch (Exception e) {
			
			// ログイン情報の破棄
			//session.invalidate();
			e.printStackTrace();
			throw e;
			
			
			//model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			// エラー画面遷移
			//return TransitionTargetPageNameKeyword.MENU_HTML;
		}
	}
}
