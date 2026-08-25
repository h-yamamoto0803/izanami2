package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.PostTagEntity;

public interface PostTagRepository extends JpaRepository<PostTagEntity, Integer>{

}
