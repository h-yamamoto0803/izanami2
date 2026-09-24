package com.example.demo.presentation.controller.post;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.post.SearchPostDetailService;
import com.example.demo.domain.service.thread.ThreadService;
import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.post.PostDetailForm;
import com.example.demo.presentation.form.thread.ThreadForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostDetailController {

    /** 投稿詳細取得Service */
    private final SearchPostDetailService searchPostDetailService;

    /** コメント処理Service */
    private final ThreadService threadService;

    /**
     * 投稿詳細画面表示
     *
     * @param model Model
     * @param postId 投稿ID
     * @param session セッション
     * @return 投稿詳細画面
     */
    @PermissionCheck
    @GetMapping(POST_DETAIL)
    public String postDtailController(
            Model model,
            @RequestParam Integer postId,
            HttpSession session) {
    	

        // ログインユーザーを取得
        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER);

        // 投稿詳細を取得
        PostEntity postEntity =
                searchPostDetailService.getPostDetail(postId);

        // 職人の場合、専門タグが投稿に付いているか確認
        if (loginUser != null && loginUser.isArtisan()) {

            if (!searchPostDetailService.canViewPostDetail(
                    loginUser.getUserId(),
                    postEntity)) {

                throw new InsufficientPermissionException(
                        "ユーザータイプが不正です");
            }
        }

        // 投稿詳細Formへ変換
        PostDetailForm postDetailForm =
                searchPostDetailService.convertFrom(postEntity);

        // ログインユーザーが存在する場合
        if (loginUser != null) {

            UserEntity userEntity =
                    loginUser.convertToUserEntity(loginUser);

            // いいね・検討フラグ情報を追加
            postDetailForm =
                    searchPostDetailService.alreadyFlag(
                            postDetailForm,
                            userEntity,
                            postEntity);
        }

        // ログインユーザーIDを取得
        Integer loginUserId =
                loginUser != null
                        ? loginUser.getUserId()
                        : null;

        // 対象投稿の未削除コメントを取得
        List<ThreadForm> threadList =
                threadService.findByPostId(
                        postId,
                        loginUserId);

        // ログインフォームを画面へ渡す
        LoginUserForm loginUserForm =
                new LoginUserForm();

        model.addAttribute(
                LOGIN_FORM,
                loginUserForm);

        // 投稿詳細情報を画面へ渡す
        model.addAttribute(
                POST_DETAIL_FORM,
                postDetailForm);

        // コメント一覧を画面へ渡す
        model.addAttribute(
                "threadList",
                threadList);

        // コメント投稿フォームを作成
        ThreadForm threadForm =
                new ThreadForm();

        // コメント対象の投稿IDを設定
        threadForm.setPostId(postId);

        // コメント投稿フォームを画面へ渡す
        model.addAttribute(
                "threadForm",
                threadForm);

       
        return POST_DETAIL_HTML;
    }
}