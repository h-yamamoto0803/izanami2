package com.example.demo.presentation.form.common;

import java.sql.Timestamp;

import com.example.demo.infra.entity.NotificationEntity;

import lombok.Data;

@Data
public class NotificationsForm {
	
	/**
	 * 投稿ID
	 */
	private Integer postId;
	
	/**
	 * メッセージ
	 */
	private String message;
	
	/**
	 * 作成日
	 */
	private Timestamp createdAt;
	
	/**
	 * 既読
	 */
	private Boolean isRead;
	
	/**
	 *  NotificationEntityをNotificationsFormに変換するメソッド
	 * @param entity
	 * @return NotificationsForm
	 */
    public static NotificationsForm convertFrom(NotificationEntity entity) {

        NotificationsForm form = new NotificationsForm();
        
        //　NotificationEntity→CandidateEntity→PostEntity→getPostId()
        form.setPostId(
                entity.getCandidate()
                      .getPost()
                      .getPostId()
            );
        
        form.setMessage(entity.getMessage());
        form.setIsRead(entity.getIsRead());
        form.setCreatedAt(entity.getCreatedAt());

        return form;
    }
}
