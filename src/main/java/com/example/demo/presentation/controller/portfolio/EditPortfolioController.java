package com.example.demo.presentation.controller.portfolio;

import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.portfolio.PortfolioService;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EditPortfolioController {

    private final PortfolioService portfolioService;

    @PermissionCheck
    @GetMapping(EDIT_PORTFOLIO)
    public String portfolioEdit(
            @RequestParam Integer portfolioId,
            Model model,
            HttpSession session) {

        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        LOGIN_USER);

        Integer userId = loginUser.getUserId();

        PortfolioForm form =
                portfolioService.findPortfolio(
                        portfolioId,
                        userId);

        model.addAttribute(
                "portfolioForm",
                form);

        return EDIT_PORTFOLIO_HTML;
    }
}