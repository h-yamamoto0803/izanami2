package com.example.demo.presentation.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

@Controller
public class DeleteCustomerAccountController {
	@RequestMapping(value = TransitionTargetPageNameKeyword.DELETE_CUSTOMER_ACCOUNT, method = RequestMethod.POST)
	public String deleteCustomerAccount() {
		System.out.println("アカウント削除処理");
		return TransitionTargetPageNameKeyword.MENU_HTML;
		
	}
}
