package com.example.demo.domain.service.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.comment.CommentService;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.post.PostDetailForm;
import com.example.demo.presentation.form.post.PostDetailViewData;
import com.example.demo.presentation.form.thread.ThreadForm;

import lombok.RequiredArgsConstructor;

/**
 * 投稿詳細画面表示に必要なデータを取得・作成するService
 *
 * 投稿詳細画面では、
 * ・投稿詳細情報
 * ・コメント一覧
 *
 * の両方が必要になる。
 *
 * PostDetailControllerだけでなく、
 * ThreadControllerのコメント投稿時のバリデーションエラーでも
 * 同じ情報が必要になるため、共通化。
 */
@Service
@RequiredArgsConstructor
public class PostDetailViewService {

    /** 投稿詳細取得Service */
    private final SearchPostDetailService searchPostDetailService;

    /** コメント処理Service */
    private final CommentService commentService;

    /**
     * 投稿詳細画面に必要な情報をまとめて取得する。
     *
     * 投稿詳細情報とコメント一覧を取得し、
     * PostDetailViewDataにまとめて返す。
     *
     * Modelへの設定はController側で行うため、
     * このServiceではModelを使用しない。
     *
     * @param postId 投稿ID
     * @param loginUser ログインユーザー
     * @return 投稿詳細画面表示用データ
     */
    public PostDetailViewData createPostDetailViewData(
            Integer postId,
            LoginUserForm loginUser) {

        // 投稿詳細を取得
        PostEntity postEntity =
                searchPostDetailService.getPostDetail(postId);

        // 投稿詳細EntityをFormへ変換
        PostDetailForm postDetailForm =
                searchPostDetailService.convertFrom(postEntity);

        /*
         * ログインユーザーが存在する場合、
         * いいね・検討フラグの情報を投稿詳細Formへ設定する。
         */
        if (loginUser != null) {

            UserEntity userEntity =
                    loginUser.convertToUserEntity(loginUser);

            postDetailForm =
                    searchPostDetailService.alreadyFlag(
                            postDetailForm,
                            userEntity,
                            postEntity);
        }

        /*
         * ログインユーザーIDを取得する。
         *
         * 未ログインの場合はnullを渡す。
         * ThreadService側で、
         * ownCommentの判定などに使用する。
         */
        Integer loginUserId = null;

        if (loginUser != null) {
            loginUserId = loginUser.getUserId();
        }

        // 対象投稿のコメント一覧を取得
        List<ThreadForm> threadList =
        		commentService.findThreadFormsByPostId(
                        postId,
                        loginUserId);

        /*
         * 投稿詳細情報とコメント一覧をまとめて返す。
         *
         * Modelへの設定はController側で行う。
         */
        return new PostDetailViewData(
                postDetailForm,
                threadList);
    }
}

