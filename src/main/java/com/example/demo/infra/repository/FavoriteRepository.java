package com.example.demo.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.infra.entity.FavoriteEntity;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

	Optional<FavoriteEntity> findByUserAndPost(UserEntity user, PostEntity post);

	long countByPost(PostEntity post);

	boolean existsByUserAndPost(UserEntity user, PostEntity post);

	@Query("""
			    SELECT f.post.postId
			    FROM FavoriteEntity f
			    WHERE f.user = :user
			""")
	List<Integer> findPostIdsByUser(UserEntity user);

}
