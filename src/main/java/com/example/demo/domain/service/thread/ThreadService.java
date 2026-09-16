package com.example.demo.domain.service.thread;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.ThreadEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.ThreadRepository;
import com.example.demo.presentation.form.thread.ThreadForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ThreadService {

    private final ThreadRepository threadRepository;

    /**
     * 指定した投稿に紐づく未削除コメントを
     * 作成日時の昇順で取得します。
     *
     * @param postId 投稿ID
     * @param loginUserId ログインユーザーID
     * @return コメント一覧
     */
    public List<ThreadForm> findByPostId(
            Integer postId,
            Integer loginUserId) {

        // 未削除コメントのみ取得
        List<ThreadEntity> threads =
                threadRepository
                        .findByPostPostIdAndIsDeletedOrderByCreatedAtAsc(
                                postId,
                                (byte) 0);

        List<ThreadForm> result =
                new ArrayList<>();

        for (ThreadEntity thread : threads) {

            boolean ownComment = false;

            if (loginUserId != null
                    && thread.getUser() != null
                    && loginUserId.equals(
                            thread.getUser().getUserId())) {

                ownComment = true;
            }

            ThreadForm form =
                    new ThreadForm(
                            thread.getThreadId(),
                            thread.getPost().getPostId(),
                            thread.getUser().getUserId(),
                            thread.getUser().getUserName(),
                            thread.getUser().getUserType().toString(),
                            thread.getComment(),
                            thread.getCreatedAt(),
                            thread.getUpdatedAt(),
                            ownComment);

            result.add(form);
        }

        return result;
    }

    /**
     * 指定した投稿に紐づく未削除コメント件数を取得します。
     *
     * @param postId 投稿ID
     * @return コメント件数
     */
    public long countByPostId(
            Integer postId) {

        return threadRepository
                .countByPostPostIdAndIsDeleted(
                        postId,
                        (byte) 0);
    }

    /**
     * コメントを登録します。
     *
     * @param form コメント投稿フォーム
     * @param userId コメントを投稿するユーザーID
     */
    public void insertThread(
            ThreadForm form,
            Integer userId) {

        // 投稿Entityを作成
        PostEntity post =
                new PostEntity();

        post.setPostId(
                form.getPostId());

        // ユーザーEntityを作成
        UserEntity user =
                new UserEntity();

        user.setUserId(
                userId);

        // コメントEntityを作成
        ThreadEntity thread =
                new ThreadEntity();

        thread.setPost(post);
        thread.setUser(user);
        thread.setComment(form.getComment());

        // 未削除状態で登録
        thread.setIsDeleted(
                (byte) 0);

        // 作成日時・更新日時
        Timestamp now =
                new Timestamp(
                        System.currentTimeMillis());

        thread.setCreatedAt(now);
        thread.setUpdatedAt(now);

        // DB登録
        threadRepository.save(thread);
    }

    /**
     * コメントを編集します。
     *
     * 編集対象は、
     * ログインユーザー本人の未削除コメントのみとします。
     *
     * @param form コメント編集フォーム
     * @param userId ログインユーザーID
     */
    public void updateThread(
            ThreadForm form,
            Integer userId) {

        // 自分の未削除コメントのみ取得
        ThreadEntity thread =
                threadRepository
                        .findByThreadIdAndUserUserIdAndIsDeleted(
                                form.getThreadId(),
                                userId,
                                (byte) 0)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "編集対象のコメントが存在しません。"));

        // コメント本文を更新
        thread.setComment(
                form.getComment());

        // 更新日時を更新
        thread.setUpdatedAt(
                new Timestamp(
                        System.currentTimeMillis()));

        // DB更新
        threadRepository.save(thread);
    }

    /**
     * コメントを論理削除します。
     *
     * @param threadId コメントID
     * @param userId ログインユーザーID
     */
    public void deleteThread(
            Integer threadId,
            Integer userId) {

        // 自分の未削除コメントのみ取得
        ThreadEntity thread =
                threadRepository
                        .findByThreadIdAndUserUserIdAndIsDeleted(
                                threadId,
                                userId,
                                (byte) 0)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "削除対象のコメントが存在しません。"));

        // 論理削除
        thread.setIsDeleted(
                (byte) 1);

        // 更新日時
        thread.setUpdatedAt(
                new Timestamp(
                        System.currentTimeMillis()));

        // DB更新
        threadRepository.save(thread);
    }
}

