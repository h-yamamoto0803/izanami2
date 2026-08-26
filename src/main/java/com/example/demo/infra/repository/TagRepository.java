package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.TagEntity;

/**
 * tagsテーブルへのアクセスを担当するRepositoryです。
 */
public interface TagRepository extends JpaRepository<TagEntity, Integer> {

}