package com.example.demo.infra.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.infra.entity.UserEntity;
@Repository

/**
 * usersテーブルに対するDBアクセスを担当するRepositoryです。
 *
 * Repository：
 * DBへの検索・登録・更新・削除などの処理を担当します。
 *
 * 今回はログイン処理で、
 * 「入力されたメールアドレスに該当するユーザーを取得する」
 * ために使用します。
 */

public interface UserRepository extends JpaRepository<UserEntity, Integer> {


	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findById(Integer userId);
	
	
	
}

