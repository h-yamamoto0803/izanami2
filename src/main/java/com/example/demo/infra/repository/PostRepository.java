package com.example.demo.infra.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.infra.entity.PostEntity;

public interface PostRepository
extends JpaRepository<PostEntity, Integer> {
@Query(value = """
	SELECT DISTINCT p.*
	FROM posts p
	INNER JOIN post_tags pt
		ON pt.post_id = p.post_id
	INNER JOIN tags t
		ON t.tag_id = pt.tag_id
	WHERE p.is_deleted = 0
	  AND t.tag_name IN (:tagNames)
	ORDER BY p.created_at DESC
	""", nativeQuery = true)
List<PostEntity> findByAnyTagName(
	@Param("tagNames") Collection<String> tagNames);
List<PostEntity> findAllByIsDeleted(
	Byte isDeleted);
List<PostEntity> findByUserUserId(Integer userId);
}