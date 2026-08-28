package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Integer>{
	
	TagEntity findByTagName(String tagName);

}
