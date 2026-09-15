package com.example.demo.domain.service.portfolio;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.infra.repository.PortfolioTagRepository;

import lombok.RequiredArgsConstructor;

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
            Integer userId) throws IOException {

        PortfolioEntity portfolio =
                portfolioService.findByIdAndUserId(
                        portfolioId,
                        userId
                );

        String imagePath = portfolio.getImagePath();

        // ポートフォリオに紐づくタグを削除
        portfolioTagRepository.deleteByIdPortfolioId(
                portfolioId
        );

        // ポートフォリオ本体を削除
        portfolioRepository.delete(portfolio);

        // 画像を削除
        imageService.deleteImage(imagePath);

        return "ポートフォリオを削除しました。";
    }
}