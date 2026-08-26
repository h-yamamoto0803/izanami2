package com.example.demo.presentation.controller.customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.service.customer.DeleteCustomerAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class DeleteCustomerAccountController {
	HttpSession httpSession;
	LoginUserForm loginUserForm;
	DeleteCustomerAccount deleteCustomerAccount;
	
	@Autowired
	public DeleteCustomerAccountController(HttpSession httpSession,DeleteCustomerAccount deleteCustomerAccount) {
		this.httpSession = httpSession;
		this.deleteCustomerAccount = deleteCustomerAccount;
	}
	
	@RequestMapping(value = TransitionTargetPageNameKeyword.DELETE_CUSTOMER_ACCOUNT, method = RequestMethod.GET)
	public String deleteCustomerAccount(Model model,
            @ModelAttribute CustomerAccountEditForm customerAccountEditForm,
            HttpSession session) {
        
        
		
		try {
			LoginUserForm loginUser =
                (LoginUserForm)session.getAttribute(SessionKeyword.LOGIN_USER);
        CustomerAccountEditForm beforecustomerAccountEditForm 
        = deleteCustomerAccount.searchIdCustomer(loginUser.getUserId()); 
        
        UserEntity deleteUserEntity = CustomerAccountEditForm.convertTo(customerAccountEditForm);
        deleteUserEntity.setIsDeleted((byte)1);
        deleteCustomerAccount.deleteCustomer(
                beforecustomerAccountEditForm, 
                deleteUserEntity);

        
		System.out.println("アカウント削除処理完了");
		httpSession.invalidate();
		
		return TransitionTargetPageNameKeyword.MENU_HTML;
		
	}catch (Exception e) {
        // ログイン情報の破棄
        httpSession.invalidate();

        e.printStackTrace();
        model.addAttribute("msg","予期せぬエラーが発生しました。" );
        System.out.println("予期せぬエラー");
        return TransitionTargetPageNameKeyword.MENU_HTML;
    }
	}
	
	
}
