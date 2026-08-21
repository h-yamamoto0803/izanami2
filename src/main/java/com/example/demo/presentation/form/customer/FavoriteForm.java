package com.example.demo.presentation.form.customer;

import com.example.demo.infra.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * いいねが押された際のフォーム情報をcontrollerが取得するためのformクラス
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteForm {

	private Integer postId;

	/**
	 * 投稿IDを格納したPostEntityを返す
	 * @param form
	 * @return PostEntity
	 */
	public PostEntity convertToPostEntity(FavoriteForm form) {
		PostEntity post = new PostEntity();

		post.setPostId(form.getPostId());
		return post;
	}

}
