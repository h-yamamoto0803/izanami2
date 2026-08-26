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

import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class ConfirmCustomerAccountEditController {
	
	@Autowired
	UpdateCustomerAccount updateCustomerAccountTest;
	HttpSession httpSession;
	
	public ConfirmCustomerAccountEditController(UpdateCustomerAccount updateCustomerAccountTest,HttpSession httpSession) {
		this.updateCustomerAccountTest = updateCustomerAccountTest;
		this.httpSession = httpSession;
	}
	
	@RequestMapping(value = TransitionTargetPageNameKeyword.
			CONFIRM_CUSTOMER_ACCOUNT_EDIT, 
			method = RequestMethod.POST)
	
	public String confirmCustomerAccountEdit(@Valid CustomerAccountEditForm customerAccountEditForm, 
			BindingResult bindingResult,Model model,HttpSession session) {
		try {
			
			final String DUPLICATION="既に登録済みのメールアドレスです。";
			
		List<UserEntity> error = updateCustomerAccountTest.searchUserByEmail(customerAccountEditForm.getEmail());
		
		LoginUserForm loginUser = (LoginUserForm)session.getAttribute(SessionKeyword.LOGIN_USER);
		String beforeUsermail =loginUser.getEmail();
		System.out.println(beforeUsermail);
		System.out.println(customerAccountEditForm.getEmail());
		if(bindingResult.hasErrors()) {
				return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
			}
		if(!error.isEmpty()) {
			if(customerAccountEditForm.getEmail().equals(beforeUsermail)) {
				 model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
				 System.out.println("メールアドレスがを変更しない場合（自分のメールアドレスをもう一度入れた場合）");
				 return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML;
			}else{
			model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
			System.out.println(DUPLICATION);
			//画面側の表示処理はまだ
			model.addAttribute("errorMessage", DUPLICATION);
			return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_HTML;
			}
			}
		
		
//	         登録確認ページに遷移
		System.out.println("保持してるパスワードは"+customerAccountEditForm.getPassword());
	        model.addAttribute("CustomerAccountEditForm", customerAccountEditForm);
	        // 登録確認画面に遷移
	        System.out.println("アカウント編集確認画面に移る");
	        System.out.println("modelで渡した後"+customerAccountEditForm.getPassword());
	        return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML;


	    } catch (Exception e) {
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
