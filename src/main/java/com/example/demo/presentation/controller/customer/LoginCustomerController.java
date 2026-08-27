package com.example.demo.presentation.controller.customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.service.LoginService;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;

import lombok.RequiredArgsConstructor;

/**
 * Customer（消費者）のログインに関する画面遷移を担当するControllerです。
 */
@RequiredArgsConstructor
@Controller
public class LoginCustomerController {
	
	private final LoginService loginService;
	
   
    
    /**
     * Customerログイン画面を表示します。
     *
     * @param loginUserForm Customerログイン画面で使用するフォーム
     * @return Customerログイン画面
     */
    @GetMapping(TransitionTargetPageNameKeyword.LOGIN_CUSTOMER_CONTROLLER)
    public String showLoginCustomer(
            @ModelAttribute(TransitionTargetPageNameKeyword.LOGIN_FORM) LoginUserForm loginUserForm) {

    	
        /*
         * Customerログイン画面を表示します。
         */
        return TransitionTargetPageNameKeyword.CUSTOMER_LOGIN_HTML;
    }

    /**
     * Customerログイン画面からログイン処理を実行します。
     *
     * @param loginUserForm Customerログイン画面から送信された入力値
     * @return Customerメニュー画面
     */
    @PostMapping(TransitionTargetPageNameKeyword.LOGIN_CUSTOMER_CONTROLLER)
    public String loginCustomer(
		@Validated @ModelAttribute(TransitionTargetPageNameKeyword.LOGIN_FORM) LoginUserForm loginUserForm,
    		BindingResult bindingResult,
            HttpSession session,
            Model model) {
    	if (bindingResult.hasErrors()) {
            return TransitionTargetPageNameKeyword.CUSTOMER_LOGIN_HTML;
        }

    	Integer user = loginService.doLogin(loginUserForm
    	        );
    	
    	 if (user != null
    	            && user == 1
    	            && loginUserForm.isCustomer()) {
    		// ログインユーザーの情報をセッションに保存
             session.setAttribute(SessionKeyword.LOGIN_USER,loginUserForm);
             
             return TransitionTargetPageNameKeyword.REDIRECT 
            		 + TransitionTargetPageNameKeyword.RETURN_MENU;
        }

        model.addAttribute(
                "errorMessage",
                "メールアドレスまたはパスワードが正しくありません。"
        );

        return TransitionTargetPageNameKeyword.CUSTOMER_LOGIN_HTML;
    }
}