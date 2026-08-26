package com.example.demo.dto;

import lombok.Data;

/**
 * 変更後の内容を画面に反映するJS用に検討追加処理の結果を画面に返すDTO
 */

@Data
public class CandidateResponseDto {

	private Boolean candidated;
	private Integer postId;
	private Long candidateCount;

}
