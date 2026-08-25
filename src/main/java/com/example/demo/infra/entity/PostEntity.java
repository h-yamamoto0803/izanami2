package com.example.demo.infra.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

// いいね処理用の最低限の実装
// FormへのConvert等おそらく必須の未実装機能あり

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Integer postId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(name = "post_title", nullable = false, length = 255)
	private String postTitle;

	@Column(name = "post_text", columnDefinition = "TEXT")
	private String postText;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private Timestamp updatedAt;

	@Column(name = "is_deleted", nullable = false)
	private Byte isDeleted = 0;

}


