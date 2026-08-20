package com.example.demo.presentation.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

@Controller
public class CustomerAccountEditController {
@RequestMapping(value = TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT, method = RequestMethod.GET)
public String customerAccountEdit() {
	System.out.println("アカウント編集に行く");
	return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
	
}

}
