package com.example.demo.presentation.form.post;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PostDetailForm {
	/**
	 * 投稿ID
	 */
	private Integer postId;

	/*
	 * ユーザータイプ
	 */
	private Byte userType;
	
	/**
	 * ユーザー名
	 */
	private String userName;
	
	/**
	 * 投稿作成日
	 */
	private Timestamp postTime;
	
	/**
	 * 投稿タイトル
	 */
	private String postTitle;

	/**
	 * 投稿テキスト
	 */
	private String postText;
	
	/**
	 * タグリスト
	 */
	private List<String> tags;
	
	/**
	 * いいね数
	 */
	private long favoriteCount;
}
