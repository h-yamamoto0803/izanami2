package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.PostTagId;

public interface PostTagRepository extends JpaRepository<PostTagEntity, PostTagId>{

}
