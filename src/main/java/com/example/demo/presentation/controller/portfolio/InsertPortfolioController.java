package com.example.demo.presentation.controller.portfolio;

import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

@Controller
public class InsertPortfolioController {

    @PermissionCheck
    @GetMapping(INSERT_PORTFOLIO)
    public String portfolioCreate(
            @ModelAttribute PortfolioForm portfolioForm,
            HttpSession session) {

        LoginUserForm loginUserForm =
                (LoginUserForm) session.getAttribute(LOGIN_USER);

        if (loginUserForm == null) {
            return REDIRECT_MENU;
        }

        return INSERT_PORTFOLIO_HTML;
    }

    /*
     * 確認画面から戻る処理
     * 入力中の値を保持したまま登録画面へ戻す
     */
    @PermissionCheck
    @PostMapping(INSERT_PORTFOLIO_RET)
    public String portfolioRet(
            PortfolioForm portfolioForm) {

        return INSERT_PORTFOLIO_HTML;
    }
}