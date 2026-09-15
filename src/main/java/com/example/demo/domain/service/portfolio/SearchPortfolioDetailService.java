package com.example.demo.domain.service.portfolio;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.TagService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.presentation.form.portfolio.PortfolioDetailForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchPortfolioDetailService {

    private final PortfolioService portfolioService;
    private final TagService tagService;

    public PortfolioDetailForm searchPortfolioDetail(Integer portfolioId) {

        PortfolioEntity entity =
                portfolioService.findById(portfolioId);

        PortfolioDetailForm form =
                new PortfolioDetailForm();

        form.setPortfolioId(entity.getPortfolioId());
        form.setUserName(entity.getUser().getUserName());
        form.setImagePath(entity.getImagePath());
        form.setDescription(entity.getDescription());
        form.setTags(
                tagService.getTagNamesByPortfolioId(portfolioId)
        );
        form.setCreatedAt(
        	    entity.getCreatedAt().toLocalDateTime()
        	);
        return form;
    }
}