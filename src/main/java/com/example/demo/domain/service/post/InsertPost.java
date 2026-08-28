package com.example.demo.domain.service.post;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.infra.repository.PostTagRepository;
import com.example.demo.infra.repository.TagRepository;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.post.InsertPostForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsertPost {
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final TagRepository tagRepository;
	private final PostTagRepository postTagRepository;
	
	/**
	 * @param insertPostForm
	 * @param userId
	 */
	public void insertPost(InsertPostForm insertPostForm, Integer userId) {
		
		/*
		 * 投稿処理 PostEntity保存
		 */
		PostEntity postEntity = new PostEntity();
        postEntity.setPostTitle(insertPostForm.getPostTitle());
        postEntity.setPostText(insertPostForm.getPostText());
        postEntity.setUser(userRepository.findById(userId).orElseThrow());
		
        postRepository.save(postEntity);
        
        /*
         * タグリストにあるかないか検索して新規追加
         * TagEntity保存
         */
        for (String tag : insertPostForm.getTags()) {

            TagEntity tagEntity = tagRepository.findByTagName(tag);

            if (tagEntity == null) {
                tagEntity = new TagEntity();
                tagEntity.setTagName(tag);
                tagEntity = tagRepository.save(tagEntity);
            }

            //PostTagEntity保存
            PostTagEntity postTagEntity = new PostTagEntity();
            postTagEntity.setPost(postEntity);
            postTagEntity.setTag(tagEntity);

            postTagRepository.save(postTagEntity);
        }
    }
	
}
