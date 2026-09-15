package com.example.demo.presentation.controller.thread;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.service.thread.ThreadService;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.thread.ThreadForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ThreadController {

    /** コメント処理を行うService */
    private final ThreadService threadService;

    /**
     * コメント投稿処理
     *
     * @param postId 投稿対象の投稿ID
     * @param comment コメント本文
     * @param session ログインユーザー情報取得用
     * @param redirectAttributes リダイレクト先へのメッセージ受け渡し用
     * @return 投稿詳細画面
     */
    @PostMapping("/thread")
    public String insertThread(
            @RequestParam Integer postId,
            @RequestParam String comment,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // ログインユーザー情報を取得
        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER);

        // コメント未入力チェック
        if (comment == null || comment.trim().isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "コメントを入力してください。");

            return "redirect:" + POST_DETAIL
                    + "?postId=" + postId;
        }

        // コメント文字数チェック
        if (comment.length() > 1000) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "コメントは1000文字以内で入力してください。");

            return "redirect:" + POST_DETAIL
                    + "?postId=" + postId;
        }

        // ログインユーザーIDを取得
        Integer userId = loginUser.getUserId();

        // コメント投稿フォームを作成
        ThreadForm form = new ThreadForm();

        // 投稿ID
        form.setPostId(postId);

        // コメント本文
        form.setComment(comment);

        // コメント登録
        threadService.insertThread(form, userId);

        // 投稿詳細画面へ戻る
        return "redirect:" + POST_DETAIL
                + "?postId=" + postId;
    }

    /**
     * コメント編集処理
     *
     * @param threadId コメントID
     * @param comment 編集後のコメント
     * @param postId 投稿ID
     * @param session ログインユーザー情報取得用
     * @param redirectAttributes リダイレクト先へのメッセージ受け渡し用
     * @return 投稿詳細画面へリダイレクト
     */
    @PostMapping("/thread/update")
    public String updateThread(
            @RequestParam Integer threadId,
            @RequestParam String comment,
            @RequestParam Integer postId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // ログインユーザー情報を取得
        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER);

        // コメント未入力チェック
        if (comment == null || comment.trim().isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "コメントを入力してください。");

            return "redirect:" + POST_DETAIL
                    + "?postId=" + postId;
        }

        // コメント文字数チェック
        if (comment.length() > 1000) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "コメントは1000文字以内で入力してください。");

            return "redirect:" + POST_DETAIL
                    + "?postId=" + postId;
        }

        // ログインユーザーIDを取得
        Integer userId = loginUser.getUserId();

        // コメント更新
        threadService.updateThread(
                threadId,
                comment,
                userId);

        // 投稿詳細画面へ戻る
        return "redirect:" + POST_DETAIL
                + "?postId=" + postId;
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

        // ログインユーザー情報を取得
        LoginUserForm loginUser =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER);

        // ログインユーザーIDを取得
        Integer userId = loginUser.getUserId();

        // コメントを削除
        threadService.deleteThread(
                threadId,
                userId);

        // 投稿詳細画面へ戻る
        return "redirect:" + POST_DETAIL
                + "?postId=" + postId;
    }
}

