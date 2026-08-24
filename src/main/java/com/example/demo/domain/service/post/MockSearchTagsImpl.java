package com.example.demo.domain.service.post;

import org.springframework.stereotype.Service;

@Service
public class MockSearchTagsImpl implements SearchTags {

	public String[] getTags() {
		return new String[] {"陶芸","花器","ご近所"};
	}
	
}
