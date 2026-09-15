package com.example.demo.domain.service.portfolio;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.domain.service.common.TagService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.entity.PortfolioTagEntity;
import com.example.demo.infra.entity.PortfolioTagEntity.PortfolioTagId;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.infra.repository.PortfolioTagRepository;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditPortfolioService {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioTagRepository portfolioTagRepository;
    private final ImageService imageService;
    private final TagService tagService;

    @Transactional
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

        // 既存タグを削除
        portfolioTagRepository.deleteByIdPortfolioId(
                entity.getPortfolioId()
        );

        // 選択されたタグを登録
        List<PortfolioTagEntity> portfolioTags =
                form.getTagIds()
                        .stream()
                        .map(tagId ->
                                createPortfolioTag(
                                        entity,
                                        tagId
                                )
                        )
                        .toList();

        portfolioTagRepository.saveAll(portfolioTags);

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