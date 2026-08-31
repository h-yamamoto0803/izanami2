package com.example.demo.presentation.controller.customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class CustomerAccountEditController {

	UpdateCustomerAccount updateCustomerAccount;

	HttpSession httpSession;
	SearchCustomer searchCustomer;

	public CustomerAccountEditController(HttpSession httpsession,
			UpdateCustomerAccount updateCustomerAccountTest,
			SearchCustomer searchCustomer){
		this.httpSession = httpsession;
		this.updateCustomerAccount = updateCustomerAccountTest;
		this.searchCustomer = searchCustomer;
	}

	@PermissionCheck
	@GetMapping(TransitionTargetPageNameKeyword.ACCOUNT_EDIT)
	public String customerAccountEdit(Model model,
			@ModelAttribute CustomerAccountEditForm customerAccountEditForm,
			HttpSession session) {

		try {
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
			Integer userId = loginUser.getUserId();

			customerAccountEditForm = searchCustomer.searchIdCustomer(userId);
			model.addAttribute(PageReturnAttributeKeyword.CUSTOMER_ACCOUNT_EDIT_FORM, customerAccountEditForm);
			return TransitionTargetPageNameKeyword.ACCOUNT_EDIT_HTML;
		} catch (Exception e) {
			// ログイン情報の破棄
			session.invalidate();
			e.printStackTrace();
			model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			// エラー画面遷移
			return TransitionTargetPageNameKeyword.MENU_HTML;
		}
	}
}
