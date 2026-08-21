package com.example.demo.presentation.form.post;

import lombok.Data;

@Data
public class InsertPostForm {
	private String postTitle;
	private String postText;
	private String tag;
}
