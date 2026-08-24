package com.example.demo.presentation.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.domain.service.impl.UpdateCustomerAccountTestImpl;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class CustomerAccountEditController {
	
	@Autowired
	UpdateCustomerAccountTestImpl updateCustomerAccountTestImpl;
	
	
	@GetMapping(TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT)
public String customerAccountEdit(Model model,@ModelAttribute CustomerAccountEditForm customerAccountEditForm ) {
		
		
		model.addAttribute("customerAccountEditForm",updateCustomerAccountTestImpl.searchIdCustomer(1) );
	System.out.println("アカウント編集に行く");
	
	return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
	
}

}
