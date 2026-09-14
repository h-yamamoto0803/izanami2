package com.example.demo.domain.service.portfolio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public List<PortfolioEntity> findByUserId(Integer userId) {
        return portfolioRepository.findByUserUserId(userId);
    }
    public PortfolioEntity findById(Integer portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "ポートフォリオが見つかりません"
                        )
                );
        
    }
    public List<PortfolioEntity> findAll() {
        return portfolioRepository.findAll();
    }
    public PortfolioForm findPortfolio(
            Integer portfolioId,
            Integer userId) {

        PortfolioEntity entity =
                findByIdAndUserId(
                        portfolioId,
                        userId
                );

        PortfolioForm form = new PortfolioForm();

        form.setPortfolioId(entity.getPortfolioId());
        form.setDescription(entity.getDescription());
        form.setTempImagePath(entity.getImagePath());

        return form;
    }
    public PortfolioEntity findByIdAndUserId(
            Integer portfolioId,
            Integer userId) {

        return portfolioRepository
                .findByPortfolioIdAndUserUserId(
                        portfolioId,
                        userId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "ポートフォリオが見つかりません"
                        )
                );
    }
}