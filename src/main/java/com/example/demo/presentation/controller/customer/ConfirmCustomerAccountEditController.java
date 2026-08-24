package com.example.demo.presentation.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class ConfirmCustomerAccountEditController {
	
	
	
	@RequestMapping(value = TransitionTargetPageNameKeyword.CONFIRM_CUSTOMER_ACCOUNT_EDIT, method = RequestMethod.POST)
	public String confirmCustomerAccountEdit(CustomerAccountEditForm customerAccountEditForm, Model model) {
//		try {
	        // 登録確認ページに遷移
		System.out.println(customerAccountEditForm.getPassword());
	        model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
	        // 登録確認画面に遷移
	        System.out.println("アカウント編集確認画面に移る");
	        System.out.println("modelで渡した後"+customerAccountEditForm.getPassword());
	        return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML;


//	    } catch (Exception e) {
//	        // ログイン情報の破棄
//	        httpSession.invalidate();
//
//	        e.printStackTrace();
//
//	        // エラーログ出力
//	        LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
//	        // エラー画面遷移
//	        return TransitionTargetPageNameKeyword.ERROR_HTML;
//	    }
		
		
		
	}
}
