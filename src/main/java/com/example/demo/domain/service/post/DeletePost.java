package com.example.demo.domain.service.post;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePost {

	private final PostRepository postRepository;

	public String deletePost(Integer postId,
						Integer userId) {

		/*
		 * PostEntity を検索した結果を入れる変数
		 * 投稿が存在する → PostEntityが入っている
		 * 投稿が存在しない → Optional.empty()
		 */
		Optional<PostEntity> optionalPost = postRepository.findById(postId);

		// 投稿が存在しない場合
		if (optionalPost.isEmpty()) {
			return "投稿が存在しません。";
		}

		PostEntity postEntity = optionalPost.get();

		// すでに削除済みの場合
		if (postEntity.getIsDeleted() == 1) {
			return "既に削除されています。";
		}
		
		// 投稿者IDを取得
		Integer postUserId = postEntity.getUser().getUserId();

		//ユーザーIDが一致しない場合
		if (!postUserId.equals(userId)) {
			return "ユーザーIDが不正です。";
		}

		// 論理削除
		postEntity.setIsDeleted((byte) 1);
		postRepository.save(postEntity);
		return "削除が完了しました。";
	}
}
