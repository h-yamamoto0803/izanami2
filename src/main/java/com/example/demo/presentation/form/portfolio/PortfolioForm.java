package com.example.demo.presentation.form.portfolio;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PortfolioForm {
	/** ポートフォリオId*/
	private Integer portfolioId;
	 /** 作品画像 */
    private MultipartFile image;
    /** 作品説明 */
    @Size(max = 2000, message = "作品説明は2000文字以内で入力してください")
    private String description;
    /** 確認画面表示用の一時画像パス */
	private String tempImagePath;
	/** 選択されたタグID一覧 */
	@NotEmpty(message = "タグを1つ以上選択してください")
	private List<Integer> tagIds;

}
