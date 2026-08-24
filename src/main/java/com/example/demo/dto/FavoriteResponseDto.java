package com.example.demo.dto;

import lombok.Data;

/**
 * 変更後の内容を画面に反映するJS用にいいね処理の結果を画面に返すDTO
 */

@Data
public class FavoriteResponseDto {

	private Boolean favorited;
	private Integer postId;
	private Long favoriteCount;

}
