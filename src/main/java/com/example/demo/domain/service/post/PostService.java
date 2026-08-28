package com.example.demo.domain.service.post;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.ArtisanTagRepository;
import com.example.demo.infra.repository.FavoriteRepository;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.infra.repository.PostTagRepository;
import com.example.demo.infra.repository.TagRepository;
import com.example.demo.infra.repository.UserRepository;
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
	private final UserRepository userRepository;

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
	 * ユーザー種別に応じて投稿を検索します。
	 *
	 * @param userId ログインユーザーのID
	 * @param selectedTag 画面で選択されたタグ
	 * @return メニュー画面に表示する投稿一覧
	 */
	public List<PostListForm> searchPostByUserType(Integer userId, String selectedTag) {
		// ログインユーザーを取得
		UserEntity user = userRepository.findById(userId).orElse(null);

		// Artisanの場合は専門タグに関連する投稿を検索
		if (user.getUserType() == 2) {
			return searchPostsByArtisanTags(userId, selectedTag);
		}

		// Customerなど、それ以外の場合は全投稿を検索
		return searchAllPosts(selectedTag);
	}

	/**
	 * 全投稿を検索します。
	 *
	 * タグが選択されていない場合は全投稿を取得し、
	 * タグが選択されている場合は選択されたタグが付いている投稿を取得します。
	 *
	 * @param selectedTag 画面で選択されたタグ
	 * @return 投稿一覧
	 */
	private List<PostListForm> searchAllPosts(String selectedTag) {

		List<PostEntity> posts;

		// タグが選択されていない場合は削除されていない投稿を全件取得
		if (selectedTag == null || selectedTag.isEmpty()) {

			posts = postRepository.findAllByIsDeleted((byte) 0);
		} else {
			// 選択されたタグが付いている投稿を取得
			posts = postRepository.findByAnyTagName(List.of(selectedTag));
		}
		// Entityを画面表示用Formに変換
		return convertToPostListForm(posts);
	}

	/**
	 * Artisan用の投稿を検索します。
	 *
	 * タグが選択されていない場合は、
	 * Artisanに設定されている専門タグを使用して投稿を検索します。
	 *
	 * タグが選択されている場合は、
	 * 選択されたタグを使用して投稿を検索します。
	 *
	 * @param userId ArtisanのユーザーID
	 * @param selectedTags 画面で選択されたタグ
	 * @return 投稿一覧
	 */
	private List<PostListForm> searchPostsByArtisanTags(
			Integer userId,
			String selectedTag) {

		// Artisanに設定されている専門タグを取得
		List<TagEntity> tags = artisanTagRepository.findTagsByUserId(userId);

		// 専門タグがない場合は投稿を表示しない
		if (tags.isEmpty()) {
			return List.of();
		}

		// 検索に使用するタグ名
		List<String> tagNames;

		if (selectedTag == null || selectedTag.isEmpty()) {

			// タグ未選択の場合は、Artisanの専門タグをすべて使用
			tagNames = tags.stream()
					.map(TagEntity::getTagName)
					.toList();
		} else {

			// 選択されたタグがArtisanの専門タグに含まれているか確認
			boolean isSpecialtyTag = false;

			for (TagEntity tag : tags) {

				if (tag.getTagName().equals(selectedTag)) {
					isSpecialtyTag = true;
					break;
				}
			}

			// 専門タグに含まれていない場合は投稿を表示しない
			if (!isSpecialtyTag) {
				return List.of();
			}

			// 選択されたタグを検索に使用
			tagNames = List.of(selectedTag);
		}
		// 指定されたタグが付いている投稿を取得
		List<PostEntity> posts = postRepository.findByAnyTagName(tagNames);
		// Entityを画面表示用Formに変換
		return convertToPostListForm(posts);
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

		// ユーザーIDがない場合は空の一覧を返す
		if (userId == null) {
			return List.of();
		}

		// Artisanに設定されているタグを取得し、
		// タグ名だけを取り出して返す
		return artisanTagRepository.findTagsByUserId(userId)
				.stream()
				.map(TagEntity::getTagName)
				.toList();
	}

	/**
	 * 投稿Entityを画面表示用のPostListFormに変換します。
	 *
	 * 投稿に紐づいているタグと、投稿のいいね数も取得します。
	 *
	 * @param posts 投稿Entityの一覧
	 * @return PostListFormの一覧
	 */
	public List<PostListForm> convertToPostListForm(List<PostEntity> posts) {

		List<PostListForm> result = new java.util.ArrayList<>();

		// 投稿を1件ずつ処理
		for (PostEntity post : posts) {

			// 投稿に紐づいているタグ名を格納するList
			List<String> tags = new java.util.ArrayList<>();

			// post_tagsから、この投稿に紐づいているタグを取得
			List<PostTagEntity> postTags = postTagRepository.findByIdPostId(post.getPostId());

			// 投稿に紐づいているタグを1件ずつ処理
			for (PostTagEntity postTag : postTags) {

				// tag_idを使用してタグを取得
				TagEntity tag = tagRepository
						.findById(postTag.getId().getTagId())
						.orElse(null);

				// タグが存在する場合はタグ名をListに追加
				if (tag != null) {
					tags.add(tag.getTagName());
				}
			}

			// PostEntityをPostListFormに変換
			PostListForm form = new PostListForm(
					post.getPostId(),
					post.getUser().getUserType().toString(),
					post.getUser().getUserName(),
					post.getCreatedAt().toString(),
					post.getPostTitle(),
					post.getPostText(),
					tags,
					(int) favoriteRepository.countByPost(post));

			// 変換したFormを結果に追加
			result.add(form);
		}
		// 変換した投稿一覧を返す
		return result;
	}

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

	public List<PostEntity> findByUserId(Integer userId) {
		return postRepository.findByUserUserId(userId);
	}
}
