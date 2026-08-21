package com.example.demo.domain.service.post;

import com.example.demo.presentation.form.post.PostDetailForm;

public interface SearchPost {
	PostDetailForm getPostDetail (Integer postId);
}
