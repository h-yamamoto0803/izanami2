package com.example.demo.presentation.controller.artisan;

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
 * Artisan（職人）のログインに関する画面遷移を担当するControllerです。
 */
@Controller
public class LoginArtisanController {

	private final LoginService loginService;
	public LoginArtisanController(LoginService loginService) {
		 this.loginService = loginService;
	}
    /**
     * Artisanログイン画面を表示します。
     *
     * @param loginUserForm Artisanログイン画面で使用するフォーム
     * @return Artisanログイン画面
     */
    @GetMapping(TransitionTargetPageNameKeyword.LOGIN_ARTISAN_CONTROLLER)
    public String showLoginArtisan(
            @ModelAttribute("loginForm") LoginUserForm loginUserForm) {

        /*
         * Artisanログイン画面を表示します。
         */
        return TransitionTargetPageNameKeyword.ARTISAN_LOGIN_HTML;
    }

    /**
     * Artisanログイン画面からログイン処理を実行します。
     *
     * @param loginUserForm Artisanログイン画面から送信された入力値
     * @param session ログイン情報を保持するセッション
     * @return Artisanメニュー画面
     */
    @PostMapping(TransitionTargetPageNameKeyword.LOGIN_ARTISAN_CONTROLLER)
    public String loginArtisan(
    		
            @ModelAttribute("loginForm") LoginUserForm loginUserForm,
            HttpSession session,Model model) {
    		Integer userType = 2;
    		Integer loginResult = loginService.doLogin(
    	            loginUserForm,
    	            userType
    	    );

            if (loginResult == 1) {

              
                session.setAttribute("userType", "artisan");

                System.out.println("loginResult = " + loginResult);
                return TransitionTargetPageNameKeyword.ARTISAN_MENU_HTML;
            }
            model.addAttribute(
                    "errorMessage",
                    "メールアドレスまたはパスワードが正しくありません。"
            );
           //エラーメッセを出すようにする
            //メッセージをHTMLに渡す感じでやる
            return TransitionTargetPageNameKeyword.ARTISAN_LOGIN_HTML;
    	
        

        
    }
}