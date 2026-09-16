package com.example.demo.presentation.form.portfolio;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class PortfolioDetailForm {

    /** ポートフォリオID */
    private Integer portfolioId;

    /** 職人名 */
    private String userName;

    /** 作品画像 */
    private String imagePath;

    /** 作品説明 */
    private String description;

    /** タグ名一覧 */
    private List<String> tags;
    
    private LocalDateTime createdAt;
}