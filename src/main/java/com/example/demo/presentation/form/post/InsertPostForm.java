package com.example.demo.presentation.form.post;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class InsertPostForm {
	
	@NotBlank
	private String postTitle;
	
	@NotBlank
	private String postText;
	
	@NotBlank
	private String[] tags;
}
