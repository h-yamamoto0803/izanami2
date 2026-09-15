package com.example.demo.domain.service.portfolio;

import java.io.IOException;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.domain.service.common.TagService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.entity.PortfolioTagEntity;
import com.example.demo.infra.entity.PortfolioTagEntity.PortfolioTagId;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.infra.repository.PortfolioTagRepository;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class InsertPortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ImageService imageService;
    private final TagService tagService;
    private final PortfolioTagRepository portfolioTagRepository;

    @Transactional
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
        // タグ登録
        List<PortfolioTagEntity> portfolioTags =
                form.getTagIds()
                        .stream()
                        .map(tagId ->
                                createPortfolioTag(entity, tagId))
                        .toList();
       
        portfolioTagRepository.saveAll(portfolioTags);
        
    }
    // ポートフォリオとタグの紐づけ情報を作成
    private PortfolioTagEntity createPortfolioTag(
            PortfolioEntity portfolio,
            Integer tagId) {

        TagEntity tag = tagService.findById(tagId);

        PortfolioTagEntity portfolioTag =
                new PortfolioTagEntity();

        portfolioTag.setId(
                new PortfolioTagId(
                        portfolio.getPortfolioId(),
                        tagId
                )
        );

        portfolioTag.setPortfolio(portfolio);
        portfolioTag.setTag(tag);

        return portfolioTag;
    }
    }
