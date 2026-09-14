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

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;
    private final ImageService imageService;

    public String deletePortfolio(
            Integer portfolioId,
            Integer userId) throws IOException {

        PortfolioEntity portfolio =
                portfolioService.findByIdAndUserId(
                        portfolioId,
                        userId
                );

        String imagePath = portfolio.getImagePath();

        portfolioRepository.delete(portfolio);

        imageService.deleteImage(imagePath);

        return "ポートフォリオを削除しました。";
    }
}
