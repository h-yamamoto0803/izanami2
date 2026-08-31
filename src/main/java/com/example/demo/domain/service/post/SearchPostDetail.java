package com.example.demo.domain.service.post;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.CandidateRepository;
import com.example.demo.infra.repository.FavoriteRepository;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.presentation.form.post.PostDetailForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchPostDetail {

	private final PostRepository repository;
	private final PostService postService;
	private final FavoriteRepository favoriteRepository;
	private final CandidateRepository candidateRepository;

	/**
	 * postIdからPostEntityを取得するメソッド
	 * @param postId
	 * @return PostDetailForm
	 */
	// フラグ設定でEntityとセッション情報を利用するため処理を分割しeneityを返すよう変更
	public PostEntity getPostDetail(Integer postId) {
		PostEntity entity = repository.findById(postId).orElseThrow();
		return entity;
	}

	/**
	 * PostEntityからPostDetailFormへ変換するメソッド
	 * @param entity
	 * @return PostDetailForm
	 */
	public PostDetailForm convertFrom(PostEntity entity) {
		PostDetailForm form = new PostDetailForm();

		form.setPostId(entity.getPostId());
		form.setUserType(entity.getUser().getUserType());
		form.setUserName(entity.getUser().getUserName());
		form.setPostTime(entity.getCreatedAt());
		form.setPostTitle(entity.getPostTitle());
		form.setPostText(entity.getPostText());
		form.setTags(postService.getTagNamesByPostId(entity.getPostId()));
		form.setFavoriteCount(favoriteRepository.countByPost(entity));
		form.setCandidateCount(candidateRepository.countByPost(entity));
		return form;
	}

	public PostDetailForm alreadyFlag(PostDetailForm form, UserEntity user, PostEntity post) {
		form.setFavorited(favoriteRepository.existsByUserAndPost(user, post));
		form.setCandidated(candidateRepository.existsByUserAndPost(user, post));

		return form;
	}
}
