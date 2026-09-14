package com.example.demo.presentation.controller.portfolio;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.portfolio.PortfolioService;
import com.example.demo.infra.entity.PortfolioEntity;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class PortfolioDetailController {

    private final PortfolioService portfolioService;

    @GetMapping(DETAIL_PORTFOLIO)
    public String portfolioDetail(
            @RequestParam Integer portfolioId,
            Model model) {

        PortfolioEntity portfolio =
                portfolioService.findById(portfolioId);

        model.addAttribute("portfolio", portfolio);

        return PORTFOLIO_DETAIL_HTML;
    }
}
