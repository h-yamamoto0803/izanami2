package com.example.demo.domain.service.portfolio;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.repository.PortfolioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ImageService imageService;

    public String deletePortfolio(
            Integer portfolioId,
            Integer userId) throws IOException {

        PortfolioEntity portfolio =
                portfolioRepository
                        .findByPortfolioIdAndUserUserId(
                                portfolioId,
                                userId
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "ポートフォリオが見つかりません"
                                )
                        );

        // DB削除後に画像削除するため、先に画像パスを保持
        String imagePath = portfolio.getImagePath();

        // DB削除
        portfolioRepository.delete(portfolio);

        // 画像削除
        imageService.deleteImage(imagePath);

        return "ポートフォリオを削除しました。";
    }
}