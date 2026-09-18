package com.example.demo.presentation.controller.thread;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.post.SearchPostDetailService;
import com.example.demo.domain.service.thread.ThreadService;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.post.PostDetailForm;
import com.example.demo.presentation.form.thread.ThreadForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ThreadController {

    /** コメント処理を行うService */
    private final ThreadService threadService;

    /** 投稿詳細取得Service */
    private final SearchPostDetailService searchPostDetailService;

    /**
     * コメント投稿処理
     *
     * 新規コメント投稿時はThreadFormの
     * @NotBlank、@Sizeによるバリデーションを行う。
     *
     * @param form コメント投稿フォーム
     * @param bindingResult バリデーション結果
     * @param model Model
     * @param session ログインユーザー情報取得用
     * @return 投稿詳細画面
     */
    @PostMapping("/thread")
    public String insertThread(
            @Validated
            @ModelAttribute("threadForm")
            ThreadForm form,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        // ログインユーザーを取得
        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER);

        /*
         * ログインしていない場合
         */
        if (loginUser == null) {

            model.addAttribute(
                    "errorMessage",
                    "コメントを投稿するにはログインしてください。");

            return POST_DETAIL_HTML;
        }

        /*
         * 新規コメント投稿のバリデーション
         *
         * ThreadFormの
         * @NotBlank
         * @Size(max = 1000)
         *
         * をSpring Validationでチェックする。
         */
        if (bindingResult.hasErrors()) {

            // 投稿詳細を取得
            PostEntity postEntity =
                    searchPostDetailService.getPostDetail(
                            form.getPostId());

            // 投稿詳細Formへ変換
            PostDetailForm postDetailForm =
                    searchPostDetailService.convertFrom(
                            postEntity);

            // ログインユーザーEntityへ変換
            UserEntity userEntity =
                    loginUser.convertToUserEntity(
                            loginUser);

            // いいね・検討情報を設定
            postDetailForm =
                    searchPostDetailService.alreadyFlag(
                            postDetailForm,
                            userEntity,
                            postEntity);

            // 投稿詳細情報をModelへ設定
            model.addAttribute(
                    POST_DETAIL_FORM,
                    postDetailForm);

            // コメント一覧を取得
            List<ThreadForm> threadList =
                    threadService.findByPostId(
                            form.getPostId(),
                            loginUser.getUserId());

            // コメント一覧をModelへ設定
            model.addAttribute(
                    "threadList",
                    threadList);

            // ログインフォームをModelへ設定
            LoginUserForm loginUserForm =
                    new LoginUserForm();

            model.addAttribute(
                    LOGIN_FORM,
                    loginUserForm);

            /*
             * 新規投稿の場合のみ、
             * バリデーションエラーとなった
             * ThreadFormをそのままModelへ戻す。
             *
             * これにより、新規コメント欄には
             * 入力内容とエラーメッセージが表示される。
             */
            model.addAttribute(
                    "threadForm",
                    form);

            return POST_DETAIL_HTML;
        }

        // ログインユーザーIDを取得
        Integer userId =
                loginUser.getUserId();

        // コメント登録
        threadService.insertThread(
                form,
                userId);

        // 登録成功時は投稿詳細へリダイレクト
        return "redirect:"
                + POST_DETAIL
                + "?postId="
                + form.getPostId();
    }

    /**
     * コメント編集処理
     *
     * 編集時もThreadFormの
     * @NotBlank、@Sizeによるバリデーションを行う。
     *
     * バリデーションエラーの場合は、
     * 新規コメント欄へエラー内容を渡さず、
     * 投稿詳細画面へ戻るだけとする。
     *
     * @param form コメント編集フォーム
     * @param bindingResult バリデーション結果
     * @param session ログインユーザー情報取得用
     * @return 投稿詳細画面
     */
    @PostMapping("/thread/update")
    public String updateThread(
            @Validated
            @ModelAttribute("threadForm")
            ThreadForm form,
            BindingResult bindingResult,
            HttpSession session) {

        // ログインユーザーを取得
        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER);

        /*
         * 未ログインの場合
         *
         * 編集処理を行わず投稿詳細へ戻る。
         */
        if (loginUser == null) {

            return "redirect:"
                    + POST_DETAIL
                    + "?postId="
                    + form.getPostId();
        }

        /*
         * 編集時のバリデーション
         *
         * ThreadFormの
         * @NotBlank
         * @Size(max = 1000)
         *
         * を使用する。
         *
         * エラーの場合は、
         * 新規コメント用のModelへ
         * ThreadFormを設定しない。
         *
         * そのため、新規コメント欄へ
         * 編集時のエラー内容は表示されない。
         */
        if (bindingResult.hasErrors()) {

            return "redirect:"
                    + POST_DETAIL
                    + "?postId="
                    + form.getPostId();
        }

        // ログインユーザーIDを取得
        Integer userId =
                loginUser.getUserId();

        // コメント更新
        threadService.updateThread(
                form,
                userId);

        // 更新成功時は投稿詳細画面へ戻る
        return "redirect:"
                + POST_DETAIL
                + "?postId="
                + form.getPostId();
    }

    /**
     * コメント削除処理
     *
     * @param threadId コメントID
     * @param postId 投稿ID
     * @param session ログインユーザー情報取得用
     * @return 投稿詳細画面へリダイレクト
     */
    @PostMapping("/thread/delete")
    public String deleteThread(
            @RequestParam Integer threadId,
            @RequestParam Integer postId,
            HttpSession session) {

        // ログインユーザーを取得
        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER);

        // ログインユーザーIDを取得
        Integer userId =
                loginUser.getUserId();

        // コメントを削除
        threadService.deleteThread(
                threadId,
                userId);

        // 投稿詳細画面へ戻る
        return "redirect:"
                + POST_DETAIL
                + "?postId="
                + postId;
    }
}

