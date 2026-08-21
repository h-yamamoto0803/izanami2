package com.example.demo.presentation.controller.customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.service.LoginService;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;

/**
 * Customer（消費者）のログインに関する画面遷移を担当するControllerです。
 */
@Controller
public class LoginCustomerController {
	
	private final LoginService loginService;
	public LoginCustomerController(LoginService loginService) {
		 this.loginService = loginService;
   
    }
    /**
     * Customerログイン画面を表示します。
     *
     * @param loginUserForm Customerログイン画面で使用するフォーム
     * @return Customerログイン画面
     */
    @GetMapping(TransitionTargetPageNameKeyword.LOGIN_CUSTOMER_CONTROLLER)
    public String showLoginCustomer(
            @ModelAttribute("loginForm") LoginUserForm loginUserForm) {

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
    		
            @ModelAttribute("loginForm") LoginUserForm loginUserForm,
            HttpSession session,Model model) {
    		Integer userType = 1;
    		Integer loginResult = loginService.doLogin(
    	            loginUserForm,
    	            userType
    	    );

            if (loginResult == 1) {

              
                session.setAttribute("userType", "customer");

                
                return TransitionTargetPageNameKeyword.CUSTOMER_MENU_HTML;
            }
            model.addAttribute(
                    "errorMessage",
                    "メールアドレスまたはパスワードが正しくありません。"
            );
           //エラーメッセを出すようにする
            //メッセージをHTMLに渡す感じでやる
            return TransitionTargetPageNameKeyword.CUSTOMER_LOGIN_HTML;
    	
        

        
    }
}