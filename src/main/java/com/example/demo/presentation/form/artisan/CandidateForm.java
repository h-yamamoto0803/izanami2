package com.example.demo.presentation.form.artisan;

import com.example.demo.infra.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 検討ボタンが押された際のフォーム情報をcontrollerが取得するためのformクラス
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateForm {

	private Integer postId;

	/**
	 * 投稿IDを格納したPostEntityを返す
	 * 現状いいねと異なる点は無いが、イベントとして異なるものを扱うため別物とする
	 * @param form
	 * @return PostEntity
	 */
	public PostEntity convertToPostEntity(CandidateForm form) {
		PostEntity post = new PostEntity();

		post.setPostId(form.getPostId());
		return post;
	}

}
