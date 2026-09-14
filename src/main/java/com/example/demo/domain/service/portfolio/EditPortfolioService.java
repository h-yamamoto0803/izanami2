package com.example.demo.domain.service.portfolio;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditPortfolioService {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;
    private final ImageService imageService;

    public void editPortfolio(
            PortfolioForm form,
            Integer userId) throws IOException {

        PortfolioEntity entity =
                portfolioService.findByIdAndUserId(
                        form.getPortfolioId(),
                        userId
                );

        String oldImagePath = entity.getImagePath();
        String newImagePath = oldImagePath;

        // 画像が変更されている場合
        if (!oldImagePath.equals(form.getTempImagePath())) {

            newImagePath =
                    imageService.moveToPermanent(
                            form.getTempImagePath(),
                            "portfolio"
                    );
        }

        updatePortfolioEntity(
                entity,
                form,
                newImagePath
        );

        portfolioRepository.save(entity);

        // DB更新後に旧画像を削除
        if (!oldImagePath.equals(newImagePath)) {
            imageService.deleteImage(oldImagePath);
        }
    }

    private void updatePortfolioEntity(
            PortfolioEntity entity,
            PortfolioForm form,
            String imagePath) {

        entity.setDescription(form.getDescription());
        entity.setImagePath(imagePath);
    }
}