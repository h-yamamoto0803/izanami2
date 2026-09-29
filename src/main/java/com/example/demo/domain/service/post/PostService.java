package com.example.demo.domain.service.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.TagService;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.CandidateRepository;
import com.example.demo.infra.repository.FavoriteRepository;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.infra.repository.ThreadRepository;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.post.PostListForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	/** 未削除 */
	private static final byte NOT_DELETED = 0;

	/** 削除済み */
	private static final byte DELETED = 1;

	private final PostRepository postRepository;
	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final ThreadRepository threadRepository;
	private final CandidateRepository candidateRepository;
	private final TagService tagService;

	/**
	 * ユーザーIDから投稿を取得します。
	 */
	public List<PostEntity> findByUserId(
			Integer userId) {

		return postRepository.findByUserUserId(userId);
	}

	/**
	 * ユーザーIDと削除フラグから投稿を取得します。
	 */
	public List<PostEntity> findByUserIdAndIsDeleted(
			Integer userId,
			byte isDeleted) {

		return postRepository
				.findByUserUserIdAndIsDeleted(
						userId,
						isDeleted);
	}

	/**
	 * ユーザー種別に応じて投稿を検索します。
	 */
	public List<PostListForm> searchPostByUserType(
			Integer userId,
			String selectedTag) {

		List<PostListForm> postListForm;

		if (userId == null) {
			return searchAllPosts(selectedTag);
		}

		UserEntity user = userRepository.findById(userId)
				.orElseThrow();

		if (user.getUserType() == 2) {
			return searchPostsByArtisan(
					userId,
					selectedTag);

		} else {
			postListForm = searchAllPosts(selectedTag);
		}

		return flaggedCheck(
				postListForm,
				user);
	}

	/**
	 * 全投稿を検索します。
	 */
	private List<PostListForm> searchAllPosts(
			String selectedTag) {

		List<PostEntity> posts;

		if (!hasSelectedTag(selectedTag)) {

			posts = postRepository.findAllByIsDeleted(
					NOT_DELETED);

		} else {

			posts = postRepository.findByAnyTagName(
					List.of(selectedTag));
		}

		return convertToPostListForm(posts);
	}

	/**
	 * Artisan用の投稿を検索します。
	 */
	private List<PostListForm> searchPostsByArtisan(
			Integer userId,
			String selectedTag) {

		List<String> artisanTags = tagService.getArtisanTagNames(userId);

		if (artisanTags.isEmpty()) {
			return List.of();
		}

		if (hasSelectedTag(selectedTag)
				&& !artisanTags.contains(selectedTag)) {

			return List.of();
		}

		List<String> tagNames;

		if (hasSelectedTag(selectedTag)) {

			tagNames = List.of(selectedTag);

		} else {

			tagNames = artisanTags;
		}

		List<PostEntity> posts = postRepository.findByAnyTagName(
				tagNames);

		return convertToPostListForm(posts);
	}

	/**
	 * 投稿Entityを画面表示用のPostListFormに変換します。
	 */
	public List<PostListForm> convertToPostListForm(
			List<PostEntity> posts) {

		List<PostListForm> result = new ArrayList<>();

		for (PostEntity post : posts) {

			List<String> tags = tagService.getTagNamesByPostId(
					post.getPostId());

			long commentCount = threadRepository
					.countByPostPostIdAndIsDeleted(
							post.getPostId(),
							NOT_DELETED);

			PostListForm form = new PostListForm(
					post.getPostId(),
					post.getUser()
							.getUserType()
							.toString(),
					post.getUser()
							.getUserName(),
					post.getCreatedAt()
							.toString(),
					post.getPostTitle(),
					post.getPostText(),
					tags,
					favoriteRepository
							.countByPost(post),
					false,
					false,
					commentCount);

			result.add(form);
		}

		return result;
	}

	/**
	 * いいね・検討フラグを設定します。
	 */
	private List<PostListForm> flaggedCheck(
			List<PostListForm> postListForm,
			UserEntity user) {

		List<Integer> favoritePostIdList = favoriteRepository.findPostIdsByUser(user);

		List<Integer> candidatePostIdList = candidateRepository.findPostIdsByUser(user);

		for (PostListForm post : postListForm) {

			post.setFavorited(
					favoritePostIdList.contains(
							post.getPostId()));

			post.setCandidated(
					candidatePostIdList.contains(
							post.getPostId()));
		}

		return postListForm;
	}

	private boolean hasSelectedTag(
			String selectedTag) {

		return selectedTag != null
				&& !selectedTag.isBlank();
	}
}