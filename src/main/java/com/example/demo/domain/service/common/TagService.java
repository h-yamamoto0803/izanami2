package com.example.demo.domain.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PortfolioTagEntity;
import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.repository.ArtisanTagRepository;
import com.example.demo.infra.repository.PortfolioTagRepository;
import com.example.demo.infra.repository.PostTagRepository;
import com.example.demo.infra.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {

    private final PortfolioTagRepository portfolioTagRepository;
    private final ArtisanTagRepository artisanTagRepository;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;

    /**
     * すべてのタグ名を取得する
     */
    public List<String> getAllTags() {
        return toTagNames(tagRepository.findAll());
    }

    /**
     * すべてのタグ情報を取得する
     */
    public List<TagEntity> getAllTagEntities() {
        return tagRepository.findAll();
    }

    /**
     * Artisanに設定されている専門タグの名前を取得する
     */
    public List<String> getArtisanTagNames(Integer userId) {

        if (userId == null) {
            return List.of();
        }

        return toTagNames(
                artisanTagRepository.findTagsByUserId(userId)
        );
    }

    /**
     * 指定された投稿に設定されているタグ名を取得する
     */
    public List<String> getTagNamesByPostId(Integer postId) {

        List<TagEntity> tags =
                postTagRepository
                        .findByIdPostId(postId)
                        .stream()
                        .map(PostTagEntity::getTag)
                        .toList();

        return toTagNames(tags);
    }

    /**
     * 指定されたポートフォリオに設定されているタグIDを取得する
     */
    public List<Integer> getTagIdsByPortfolioId(Integer portfolioId) {

        return portfolioTagRepository
                .findByIdPortfolioId(portfolioId)
                .stream()
                .map(portfolioTag ->
                        portfolioTag.getId().getTagId())
                .toList();
    }

    /**
     * 指定されたポートフォリオに設定されているタグ名を取得する
     */
    public List<String> getTagNamesByPortfolioId(Integer portfolioId) {

        List<TagEntity> tags =
                portfolioTagRepository
                        .findByIdPortfolioId(portfolioId)
                        .stream()
                        .map(PortfolioTagEntity::getTag)
                        .toList();

        return toTagNames(tags);
    }

    /**
     * TagEntityの一覧をタグ名一覧へ変換する
     */
    private List<String> toTagNames(List<TagEntity> tags) {
        return tags.stream()
                .map(TagEntity::getTagName)
                .toList();
    }
    /**
     * 指定したタグIDに対応するタグ情報を取得する
     */
    public TagEntity findById(Integer tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "タグが見つかりません"
                        )
                );
    }
    
    //指定されたタグID一覧に対応するタグ名一覧を取得する
    public List<String> getTagNamesByIds(List<Integer> tagIds) {
        return tagIds.stream()
                .map(this::findById)
                .map(TagEntity::getTagName)
                .toList();
    }
}