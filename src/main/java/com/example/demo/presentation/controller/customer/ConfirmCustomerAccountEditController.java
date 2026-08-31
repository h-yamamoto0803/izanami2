package com.example.demo.presentation.controller.customer;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class ConfirmCustomerAccountEditController {

	@Autowired
	UpdateCustomerAccount updateCustomerAccount;
	HttpSession httpSession;
	SearchCustomer searchCustomer;

	public ConfirmCustomerAccountEditController(UpdateCustomerAccount updateCustomerAccountTest,
			HttpSession httpSession, SearchCustomer searchCustomer) {
		this.updateCustomerAccount = updateCustomerAccountTest;
		this.httpSession = httpSession;
		this.searchCustomer = searchCustomer;
	}

	@PermissionCheck
	@PostMapping(CONFIRM_ACCOUNT_EDIT)
	public String confirmCustomerAccountEdit(@Valid CustomerAccountEditForm customerAccountEditForm,
			BindingResult bindingResult, Model model, HttpSession session) {

		try {
			//Formのバリデーションではじかれたとき
			if (bindingResult.hasErrors()) {
				return ACCOUNT_EDIT_HTML;
			}

			//パスワードが確認用と違くてはじかれたとき
			final String ERROR = "パスワードが一致していません";
			String password = customerAccountEditForm.getPassword();
			String passwordConfirm = customerAccountEditForm.getPasswordConfirm();
			String passwordError = customerAccountEditForm.validatePassword(password, passwordConfirm);

			if (passwordError.equals(ERROR)) {
				model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
				model.addAttribute(MESSAGE_ERROR, ERROR);
				return ACCOUNT_EDIT_HTML;
			}

			//既に登録済みのメールアドレスを入力してはじかれたとき
			final String DUPLICATION = "既に登録済みのメールアドレスです。";
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);

			String beforeUsermail = loginUser.getEmail();

			if (!beforeUsermail.equals(customerAccountEditForm.getEmail())) {
				List<UserEntity> userListBymail = searchCustomer.searchUserByEmail(customerAccountEditForm.getEmail());

				if (!userListBymail.isEmpty()) {
					model.addAttribute(CUSTOMER_ACCOUNT_EDIT_FORM, customerAccountEditForm);
					model.addAttribute(MESSAGE_ERROR, DUPLICATION);
					return ACCOUNT_EDIT_HTML;
				}

				// 登録確認画面に遷移
				model.addAttribute(CUSTOMER_ACCOUNT_EDIT_FORM, customerAccountEditForm);
				return ACCOUNT_EDIT_CONFIRM_HTML;
			}

			// 登録確認画面に遷移
			model.addAttribute(CUSTOMER_ACCOUNT_EDIT_FORM, customerAccountEditForm);
			return ACCOUNT_EDIT_CONFIRM_HTML;

		} catch (Exception e) {
			// ログイン情報の破棄
			session.invalidate();
			e.printStackTrace();
			model.addAttribute(MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			// エラー画面遷移
			return MENU_HTML;

		}
	}
}