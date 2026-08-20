package com.example.demo.presentation.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

@Controller
public class DoEditCustomerAccountController {
	@RequestMapping(value = TransitionTargetPageNameKeyword.DO_EDIT_CUSTOMER_ACCOUNT, method = RequestMethod.GET)
	public String doEditCustomerAccount() {
		System.out.println("アカウント編集登録処理");
		return TransitionTargetPageNameKeyword.MENU_HTML;
		
	}
}
