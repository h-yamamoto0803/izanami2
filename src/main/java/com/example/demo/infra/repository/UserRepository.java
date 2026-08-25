package com.example.demo.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.infra.entity.UserEntity;

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
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * メールアドレスを条件にユーザーを検索します。
     *
     * Spring Data JPAでは、メソッド名から検索条件を判断して
     * SQLを自動的に生成してくれます。
     *
     * findByEmail
     * ↓
     * 「emailが一致するデータを検索する」
     *
     * DBのemailにはUNIQUE制約が設定されているため、
     * 1つのメールアドレスに対してユーザーは1人だけです。
     *
     * @param email ログイン画面から入力されたメールアドレス
     * @return メールアドレスに一致するユーザー
     *
     *         ユーザーが存在する場合 → UserEntityが入る
     *         ユーザーが存在しない場合 → empty
     */
    Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findById(Integer userId);
	
	
	
}
