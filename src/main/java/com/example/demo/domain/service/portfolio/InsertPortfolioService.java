package com.example.demo.domain.service.portfolio;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class InsertPortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ImageService imageService;

    public void insertPortfolio(PortfolioForm form, Integer userId) throws IOException {

        // 一時保存画像を正式保存先へ移動
        String imagePath = imageService.moveToPermanent(
                form.getTempImagePath(),
                "portfolio"
        );

        UserEntity user = new UserEntity();
        user.setUserId(userId);

        PortfolioEntity entity = new PortfolioEntity();
        entity.setDescription(form.getDescription());
        entity.setUser(user);

        // 正式保存後のURLをDBへ登録
        entity.setImagePath(imagePath);

        portfolioRepository.save(entity);
    }
}