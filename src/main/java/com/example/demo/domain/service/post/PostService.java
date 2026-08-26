package com.example.demo.domain.service.post;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.repository.ArtisanTagRepository;
import com.example.demo.infra.repository.FavoriteRepository;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.infra.repository.PostTagRepository;
import com.example.demo.infra.repository.TagRepository;
import com.example.demo.presentation.form.post.PostListForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;
	private final PostTagRepository postTagRepository;
	private final TagRepository tagRepository;
	private final FavoriteRepository favoriteRepository;
	private final ArtisanTagRepository artisanTagRepository;

	/**
	 * 投稿一覧を取得する
	 *
	 * タグが指定されていない場合は全投稿、
	 * タグが指定されている場合は指定されたタグの投稿を取得する。
	 */
	public List<PostListForm> getPostListFromDatabase(
			Collection<String> tagNames) {

		List<PostEntity> posts;

		// タグ指定なしの場合は未削除の投稿をすべて取得
		if (tagNames == null || tagNames.isEmpty()) {
			posts = postRepository
					.findAllByIsDeleted((byte) 0);
			// タグ指定ありの場合は、そのタグが付いている投稿を取得
		} else {
			posts = postRepository.findByAnyTagName(tagNames);
		}

		// Entityを画面表示用のFormに変換
		return posts.stream()
				.map(post -> new PostListForm(
						// ユーザータイプを画面表示用の文字列に変換
						post.getPostId(),
						post.getUser().getUserType() == 1
								? "customer"
								: "artisan",
						post.getUser().getUserName(),
						post.getCreatedAt().toString(),
						post.getPostTitle(),
						post.getPostText(),
						getTagNamesByPostId(post.getPostId()),
						(int) favoriteRepository.countByPost(post)))
				.toList();
	}

	/**
	 * 投稿に付いているタグ名を取得する
	 *
	 * 投稿ID → post_tags → tags の順番でタグ名を取得する。
	 */
	public List<String> getTagNamesByPostId(Integer postId) {

		List<PostTagEntity> postTags = postTagRepository.findByIdPostId(postId);

		return postTags.stream()
				// PostTagEntityからタグIDを取得
				.map(postTag -> postTag.getId().getTagId())
				// タグIDからタグを取得
				.map(tagId -> tagRepository.findById(tagId))
				// タグが存在するものだけに絞る
				.filter(Optional::isPresent)
				// OptionalからTagEntityを取り出す
				.map(Optional::get)
				// TagEntityからタグ名を取得
				.map(TagEntity::getTagName)
				.toList();
	}

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
	 * 職人に付いているタグ名を取得する
	 *
	 *user_id →artisan_tags→tagId→tags→tag_nameの順番でタグ名を取得する。
	 */

	public List<String> getArtisanTags(Integer userId) {

		return artisanTagRepository.findTagsByUserId(userId)
				.stream()
				.map(TagEntity::getTagName)
				.toList();

	}
}
