
package com.example.demo.presentation.form.post;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostListForm {

    /** 投稿者種別（artisan / customer） */
    private String userType;

    /** 投稿者名 */
    private String userName;

    /** 投稿日時 */
    private String postDate;

    /** タイトル */
    private String title;

    /** 本文 */
    private String summary;

    /** ジャンル */
    private String genre;

    /** タグ */
    private List<String> tags;

    /** いいね数 */
    private Integer favoriteCount;
}