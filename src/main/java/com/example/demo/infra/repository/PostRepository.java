package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.PostEntity;

//Candidate処理用に暫定的な追加
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

}
