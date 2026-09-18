package com.example.demo.presentation.controller.portfolio;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.io.IOException;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.portfolio.DeletePortfolioService;
import com.example.demo.presentation.form.common.LoginUserForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeletePortfolioController {

    private final DeletePortfolioService deletePortfolioService;

    @PermissionCheck
    @PostMapping(DELETE_PORTFOLIO)
    public String deletePortfolio(
            @RequestParam Integer portfolioId,
            RedirectAttributes redirect,
            HttpSession session) throws IOException {

        // セッションからログインユーザー情報を取得
        LoginUserForm loginUserForm =
                (LoginUserForm) session.getAttribute(
                        LOGIN_USER
                );

        // ログインユーザーIDを取得
        Integer userId = loginUserForm.getUserId();

        // ポートフォリオ削除
        String deleteMessage =
                deletePortfolioService.deletePortfolio(
                        portfolioId,
                        userId
                );

        // 削除結果メッセージをリダイレクト先へ渡す
        redirect.addFlashAttribute(
                DELETE_MESSAGE,
                deleteMessage
        );

        // アカウント画面へ戻る
        return REDIRECT_ACCOUNT;
    }
}