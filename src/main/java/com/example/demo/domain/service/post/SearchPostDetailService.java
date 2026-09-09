package com.example.demo.domain.service.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.ArtisanTagRepository;
import com.example.demo.infra.repository.CandidateRepository;
import com.example.demo.infra.repository.FavoriteRepository;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.infra.repository.PostTagRepository;
import com.example.demo.presentation.form.post.PostDetailForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchPostDetailService {
	
	private final ArtisanTagRepository artisanTagRepository;
	private final PostTagRepository postTagRepository;
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
	public boolean canViewPostDetail(Integer userId, PostEntity postEntity) {

	    // 職人の専門タグを取得
	    List<TagEntity> artisanTags =
	            artisanTagRepository.findTagsByUserId(userId);

	    // 投稿に付いているタグを取得
	    List<PostTagEntity> postTags =
	            postTagRepository.findByIdPostId(postEntity.getPostId());

	    // 専門タグと投稿タグが1つでも一致すれば閲覧可能
	    for (PostTagEntity postTag : postTags) {

	        Integer postTagId = postTag.getId().getTagId();

	        for (TagEntity artisanTag : artisanTags) {

	            if (artisanTag.getTagId().equals(postTagId)) {
	                return true;
	            }
	        }
	    }

	    return false;
	}
}
