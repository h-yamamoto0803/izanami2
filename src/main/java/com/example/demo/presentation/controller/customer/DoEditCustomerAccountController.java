package com.example.demo.presentation.controller.customer;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class DoEditCustomerAccountController {

	UpdateCustomerAccount updateCustomerAccount;
	HttpSession httpSession;
	SearchCustomer searchCustomer;

	public DoEditCustomerAccountController(HttpSession httpsession,
			UpdateCustomerAccount updateCustomerAccount,
			SearchCustomer searchCustomer) {
		this.httpSession = httpsession;
		this.updateCustomerAccount = updateCustomerAccount;
		this.searchCustomer = searchCustomer;
	}
	
	@PermissionCheck
	@PostMapping(DO_EDIT_CUSTOMER_ACCOUNT)
	public String doEditCustomerAccount(Model model,
			@ModelAttribute CustomerAccountEditForm customerAccountEditForm,
			HttpSession session) {
		try {
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);

			CustomerAccountEditForm beforecustomerAccountEditForm = searchCustomer.searchIdCustomer(loginUser.getUserId());
			customerAccountEditForm.setUserId(beforecustomerAccountEditForm.getUserId());
			customerAccountEditForm.setUserType(beforecustomerAccountEditForm.getUserType());

			UserEntity updateUserEntity = CustomerAccountEditForm.convertTo(customerAccountEditForm);
			updateCustomerAccount.updateCustomer(beforecustomerAccountEditForm,updateUserEntity);
			
			loginUser.setUserName(updateUserEntity.getUserName());
			session.setAttribute(SessionKeyword.LOGIN_USER, loginUser);
			return REDIRECT_MENU;

		} catch (Exception e) {
			// ログイン情報の破棄
			session.invalidate();

			e.printStackTrace();

			model.addAttribute(MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			return REDIRECT_MENU;
		}
	}
}