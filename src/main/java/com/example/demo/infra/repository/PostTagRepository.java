package com.example.demo.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.PostTagEntity.PostTagId;

public interface PostTagRepository extends JpaRepository<PostTagEntity, PostTagId> {

	List<PostTagEntity> findByIdPostId(Integer postId);
	List<PostTagEntity> findByIdTagId(Integer tagId);
}