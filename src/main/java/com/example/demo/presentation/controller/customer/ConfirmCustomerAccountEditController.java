package com.example.demo.presentation.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

@Controller
public class ConfirmCustomerAccountEditController {
	@RequestMapping(value = TransitionTargetPageNameKeyword.CONFIRM_CUSTOMER_ACCOUNT_EDIT, method = RequestMethod.GET)
	public String confirmCustomerAccountEdit() {
		System.out.println("アカウント編集確認画面に移る");
		return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML;
		
	}
}
