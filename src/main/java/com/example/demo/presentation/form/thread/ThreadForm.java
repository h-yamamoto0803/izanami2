package com.example.demo.presentation.form.thread;

import java.sql.Timestamp;

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

	/** コメント本文 */

	private String comment;

	/** コメント作成日時 */

	private Timestamp createdAt;

	/** コメント更新日時 */

	private Timestamp updatedAt;

	/** ログインユーザー本人のコメントかを示す */

	private boolean ownComment;

}
