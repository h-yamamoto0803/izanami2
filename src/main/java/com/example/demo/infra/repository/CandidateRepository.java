package com.example.demo.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.CandidateEntity;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;

// 不具合が発覚しない限り追加実装の予定は無し
public interface CandidateRepository extends JpaRepository<CandidateEntity, Integer> {

	Optional<CandidateEntity> findByUserIdAndPostId(UserEntity user, PostEntity post);

	long countByPost(PostEntity post);

}
