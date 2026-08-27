package com.example.demo.presentation.controller.customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class CustomerAccountEditController {
	
	
	UpdateCustomerAccount updateCustomerAccount;
	 
	HttpSession httpSession;
	SearchCustomer searchCustomer;
	
	@Autowired
	public CustomerAccountEditController(HttpSession httpsession,UpdateCustomerAccount updateCustomerAccountTest,SearchCustomer searchCustomer) {
		this.httpSession = httpsession;
		this.updateCustomerAccount = updateCustomerAccountTest;
		this.searchCustomer = searchCustomer;
		
	}
	
	@GetMapping(TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT)
public String customerAccountEdit(Model model,
		@ModelAttribute CustomerAccountEditForm customerAccountEditForm,
		HttpSession session ) {
		
		try {
		System.out.println("koko");
		//ここまで後で消す
		LoginUserForm loginUser = (LoginUserForm)session.getAttribute(SessionKeyword.LOGIN_USER);
		
		Integer userId =loginUser.getUserId();
		
		System.out.println("userId"+userId+"を取得");
		
		customerAccountEditForm = searchCustomer.searchIdCustomer(userId);
		
		model.addAttribute("customerAccountEditForm",customerAccountEditForm );
	System.out.println("アカウント編集に行く");
	
	return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
		}catch (Exception e) {
	        // ログイン情報の破棄
	        session.invalidate();

	        e.printStackTrace();

	        model.addAttribute("msg","予期せぬエラーが発生しました。" );
//	        // エラー画面遷移
	    	System.out.println("予期せぬエラー");
	        return TransitionTargetPageNameKeyword.MENU_HTML;
	
}

}
}
