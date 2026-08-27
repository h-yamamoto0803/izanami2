package com.example.demo.presentation.controller.customer;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
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

	@RequestMapping(value = TransitionTargetPageNameKeyword.CONFIRM_CUSTOMER_ACCOUNT_EDIT, method = RequestMethod.POST)

	public String confirmCustomerAccountEdit(@Valid CustomerAccountEditForm customerAccountEditForm,
			BindingResult bindingResult, Model model, HttpSession session) {

		try {
			//Formのバリデーションではじかれたとき
			if (bindingResult.hasErrors()) {
				return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
			}

			//パスワードが確認用と違くてはじかれたとき
			final String ERROR = "パスワードが一致していません";
			String password = customerAccountEditForm.getPassword();
			String passwordConfirm = customerAccountEditForm.getPasswordConfirm();
			String passwordError = customerAccountEditForm.validatePassword(password, passwordConfirm);

			if (passwordError.equals(ERROR)) {
				model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
				System.out.println(ERROR);
				model.addAttribute("errorMessage", ERROR);
				return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
			}

			//既に登録済みのメールアドレスを入力してはじかれたとき
			final String DUPLICATION = "既に登録済みのメールアドレスです。";
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
			
			System.out.println(loginUser.getEmail());
			
			String beforeUsermail = loginUser.getEmail();

			if (!beforeUsermail.equals(customerAccountEditForm.getEmail())) {
				List<UserEntity> userListBymail = searchCustomer.searchUserByEmail(customerAccountEditForm.getEmail());

				if (!userListBymail.isEmpty()) {
					model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
					model.addAttribute("errorMessage", DUPLICATION);
					return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
				}

				// 登録確認画面に遷移
				model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
				return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML;
			}

			// 登録確認画面に遷移
			model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
			return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML;

		} catch (Exception e) {
			// ログイン情報の破棄
			session.invalidate();
			e.printStackTrace();
			model.addAttribute("msg", "予期せぬエラーが発生しました。");
			// エラー画面遷移
			System.out.println("予期せぬエラー");
			return TransitionTargetPageNameKeyword.MENU_HTML;

		}
	}
}