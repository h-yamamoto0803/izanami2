package com.example.demo.presentation.controller.customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class DoEditCustomerAccountController {
	
	
	UpdateCustomerAccount updateCustomerAccountTest;
	UpdateCustomerAccount updateCustomerAccount;
	HttpSession httpSession;
	@Autowired
	public DoEditCustomerAccountController(HttpSession httpsession,
			UpdateCustomerAccount updateCustomerAccountTest,
			UpdateCustomerAccount updateCustomerAccount) {
		this.httpSession = httpsession;
		this.updateCustomerAccountTest = updateCustomerAccountTest;
		this.updateCustomerAccount = updateCustomerAccount;
		
	}
	
	
	@RequestMapping(value = TransitionTargetPageNameKeyword.DO_EDIT_CUSTOMER_ACCOUNT, method = RequestMethod.POST)
	public String doEditCustomerAccount(Model model,
			@ModelAttribute CustomerAccountEditForm customerAccountEditForm,
			HttpSession session) {
		try {
		LoginUserForm loginUser =
				(LoginUserForm)session.getAttribute(SessionKeyword.LOGIN_USER);
		
		CustomerAccountEditForm beforecustomerAccountEditForm 
		= updateCustomerAccountTest.searchIdCustomer(loginUser.getUserId()); 
		
		UserEntity updateUserEntity = CustomerAccountEditForm.convertTo(customerAccountEditForm);
		
		updateCustomerAccount.updateCustomer(
				beforecustomerAccountEditForm, 
				updateUserEntity);
		
		System.out.println("アカウント編集登録処理完了");
		System.out.println("");
		return TransitionTargetPageNameKeyword.MENU_HTML;}
		catch (Exception e) {
	        // ログイン情報の破棄
	        session.invalidate();

	        e.printStackTrace();

	        model.addAttribute("msg","予期せぬエラーが発生しました。" );
//	        // エラー画面遷移
	    	System.out.println("予期せぬエラー");
	        return TransitionTargetPageNameKeyword.MENU_HTML;}
		
	}
}



