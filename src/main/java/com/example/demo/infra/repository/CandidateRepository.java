package com.example.demo.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.infra.entity.CandidateEntity;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;

// 不具合が発覚しない限り追加実装の予定は無し
public interface CandidateRepository extends JpaRepository<CandidateEntity, Integer> {

	Optional<CandidateEntity> findByUserAndPost(UserEntity user, PostEntity post);

	long countByPost(PostEntity post);

	boolean existsByUserAndPost(UserEntity user, PostEntity post);

	@Query("""
			    SELECT c.post.postId
			    FROM CandidateEntity c
			    WHERE c.user = :user
			""")
	List<Integer> findPostIdsByUser(UserEntity user);

}
