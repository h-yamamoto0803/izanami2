package com.example.demo.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.ThreadEntity;

public interface ThreadRepository
        extends JpaRepository<ThreadEntity, Integer> {

    /**
     * 指定した投稿に紐づく未削除コメントを
     * 作成日時の昇順で取得します。
     *
     * @param postId 投稿ID
     * @param isDeleted 削除フラグ
     * @return コメント一覧
     */
    List<ThreadEntity>
    findByPostPostIdAndIsDeletedOrderByCreatedAtAsc(
            Integer postId,
            Byte isDeleted);

    /**
     * 指定した投稿に紐づく未削除コメント件数を取得します。
     *
     * @param postId 投稿ID
     * @param isDeleted 削除フラグ
     * @return コメント件数
     */
    long countByPostPostIdAndIsDeleted(
            Integer postId,
            Byte isDeleted);

    /**
     * 指定したコメントが指定ユーザー本人のもので、
     * かつ指定した削除状態か確認します。
     *
     * @param threadId コメントID
     * @param userId ユーザーID
     * @param isDeleted 削除フラグ
     * @return コメント
     */
    Optional<ThreadEntity>
    findByThreadIdAndUserUserIdAndIsDeleted(
            Integer threadId,
            Integer userId,
            Byte isDeleted);
}

