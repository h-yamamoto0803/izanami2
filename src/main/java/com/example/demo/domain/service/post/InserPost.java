package com.example.demo.domain.service.post;

import com.example.demo.presentation.form.post.InsertPostForm;

public interface InserPost {
	public void insertPost(InsertPostForm insertPostForm, Integer userId);
}
