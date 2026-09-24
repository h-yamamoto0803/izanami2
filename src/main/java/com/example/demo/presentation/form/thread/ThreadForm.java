package com.example.demo.presentation.form.thread;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadForm {

	/** コメントId */
	private Integer threadId;

	/** 投稿Id */
	private Integer postId;

	/** コメント投稿者のユーザーID */
	private Integer userId;

	/** コメント投稿者名 */
	private String userName;

	/** コメント投稿者のユーザー種別 */
	private String userType;

	/**
	 * コメント本文
	 *
	 * 新規投稿・編集の両方で
	 * Spring Validationによる入力チェックを行う。
	 */
	@NotBlank(message = "コメントを入力してください。")
	@Size(max = 1000, message = "コメントは1000文字以内で入力してください。")
	private String comment;

	/** コメント作成日時 */
	private Timestamp createdAt;

	/** コメント更新日時 */
	private Timestamp updatedAt;

	/** ログインユーザー本人のコメントかを示す */
	private boolean ownComment;
}
