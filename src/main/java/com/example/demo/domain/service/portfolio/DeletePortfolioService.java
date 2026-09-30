package com.example.demo.domain.service.portfolio;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.infra.repository.PortfolioTagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletePortfolioService {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioTagRepository portfolioTagRepository;
    private final ImageService imageService;

    @Transactional
    public String deletePortfolio(
            Integer portfolioId,
            Integer userId) {

        PortfolioEntity portfolio =
                portfolioService.findByIdAndUserId(
                        portfolioId,
                        userId
                );

        String imagePath =
                portfolio.getImagePath();

        portfolioTagRepository
                .deleteByIdPortfolioId(portfolioId);

        portfolioRepository.delete(portfolio);

        try {
            imageService.deleteImage(imagePath);

        } catch (IOException e) {
        	 log.error("ポートフォリオ画像の削除に失敗しました。imagePath={}", imagePath, e);
        }

        return "ポートフォリオを削除しました。";
    }
}