package com.example.demo.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.FavoriteEntity;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;

// 不具合が発覚しない限り追加実装の予定は無し
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

	Optional<FavoriteEntity> findByUserAndPost(UserEntity user, PostEntity post);

	long countByPost(PostEntity post);

}
