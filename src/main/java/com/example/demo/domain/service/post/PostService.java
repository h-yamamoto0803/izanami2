package com.example.demo.domain.service.post;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
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

	/**
	 * メイン画面に表示する投稿一覧を取得します。
	 *
	 * 現在はDB連携前のため、仮データを返します。
	 * @param tag 
	 *
	 * @return 投稿一覧
	 */
	public List<PostListForm> getPostListFromDatabase(String tag) {

		List<PostEntity> posts = postRepository.findAll();

		return posts.stream()
				// タグが指定されている場合だけ絞り込み
				.filter(post -> {

					// タグ未指定なら全件表示
					if (tag == null || tag.isBlank()) {
						return true;
					}

					// 投稿に紐づいているタグを取得
					List<String> tagNames = getTagNamesByPostId(post.getPostId());

					// 指定されたタグを持っている投稿だけ残す
					return tagNames.contains(tag);
				})
				.map(post -> new PostListForm(
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

	public List<String> getTagNamesByPostId(Integer postId) {

		List<PostTagEntity> postTags = postTagRepository.findByIdPostId(postId);

		return postTags.stream()
				.map(postTag -> postTag.getId().getTagId())
				.map(tagId -> tagRepository.findById(tagId))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(TagEntity::getTagName)
				.toList();
	}

	public List<String> getAllTags() {
		return tagRepository.findAll()
				.stream()
				.map(TagEntity::getTagName)
				.toList();
	}
	
	public List<PostEntity> findByUserId(Integer userId){
		
		return postRepository.findByUser_UserId(userId);
	}
	
	public List<PostListForm> convertToPostListForm(
	        List<PostEntity> posts) {

	    List<PostListForm> result = new java.util.ArrayList<>();

	    for (PostEntity post : posts) {

	        // 投稿についているタグを取得
	        List<String> tags = new java.util.ArrayList<>();

	        List<PostTagEntity> postTags =
	                postTagRepository.findByIdPostId(post.getPostId());

	        for (PostTagEntity postTag : postTags) {

	            TagEntity tag = tagRepository
	                    .findById(postTag.getId().getTagId())
	                    .orElse(null);

	            if (tag != null) {
	                tags.add(tag.getTagName());
	            }
	        }

	        // PostEntity → PostListForm
	        PostListForm form = new PostListForm(
	        		post.getPostId(),
	                post.getUser().getUserType().toString(),
	                post.getUser().getUserName(),
	                post.getCreatedAt().toString(),
	                post.getPostTitle(),
	                post.getPostText(),
	                tags,
	                (int) favoriteRepository.countByPost(post)
	        );

	        result.add(form);
	    }

	    return result;
	}
	
}