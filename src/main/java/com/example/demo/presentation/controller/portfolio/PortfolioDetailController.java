package com.example.demo.presentation.controller.portfolio;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.portfolio.SearchPortfolioDetailService;
import com.example.demo.presentation.form.portfolio.PortfolioDetailForm;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class PortfolioDetailController {

	private final SearchPortfolioDetailService searchPortfolioDetailService;

    @GetMapping(DETAIL_PORTFOLIO)
    public String portfolioDetail(
            @RequestParam Integer portfolioId,
            Model model) {

    	PortfolioDetailForm portfolio =
    	        searchPortfolioDetailService
    	                .searchPortfolioDetail(portfolioId);

    	model.addAttribute("portfolio", portfolio);

        return PORTFOLIO_DETAIL_HTML;
    }
}
