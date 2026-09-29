package com.example.demo.presentation.form.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;


@Data
public class InsertPostForm {

    @NotBlank(message = "タイトルを入力してください")
    private String postTitle;

    @NotBlank(message = "投稿内容を入力してください")
    private String postText;

    @NotEmpty(message = "タグを1つ以上入力してください")
    private String[] tags;
}