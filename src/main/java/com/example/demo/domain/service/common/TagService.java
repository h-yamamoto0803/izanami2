package com.example.demo.domain.service.common;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.repository.ArtisanTagRepository;
import com.example.demo.infra.repository.PostTagRepository;
import com.example.demo.infra.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {

    private final ArtisanTagRepository artisanTagRepository;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;
    /**
	 * すべてのタグ名を取得する
	 */
    public List<String> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagEntity::getTagName)
                .toList();
    }
    /**
	 * Artisanに設定されている専門タグの名前を取得します。
	 *
	 * メニュー画面の「あなたの専門タグ」の表示に使用します。
	 *
	 * @param userId ArtisanのユーザーID
	 * @return 専門タグ名の一覧
	 */
    public List<String> getArtisanTagNames(Integer userId) {

        if (userId == null) {
            return List.of();
        }

        return artisanTagRepository.findTagsByUserId(userId)
                .stream()
                .map(TagEntity::getTagName)
                .toList();
    }
    
    /**
     * 指定された投稿に設定されているタグ名を取得します。
     *
     * @param postId 投稿ID
     * @return 投稿に設定されているタグ名の一覧
     */
    public List<String> getTagNamesByPostId(Integer postId) {

        List<PostTagEntity> postTags =
                postTagRepository.findByIdPostId(postId);

        return postTags.stream()
                .map(postTag -> postTag.getId().getTagId())
                .map(tagId -> tagRepository.findById(tagId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(TagEntity::getTagName)
                .toList();
    }
}


