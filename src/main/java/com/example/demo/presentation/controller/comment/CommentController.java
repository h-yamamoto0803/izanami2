package com.example.demo.presentation.controller.comment;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.comment.CommentService;
import com.example.demo.domain.service.post.PostDetailViewService;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.post.PostDetailViewData;
import com.example.demo.presentation.form.thread.ThreadForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommentController {

	/** コメント処理を行うService */
	private final CommentService commentService;

	/** 投稿詳細画面表示用Service */
	private final PostDetailViewService postDetailViewService;

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
	@PostMapping(COMMENT)
	public String insertComment(
	        @Valid @ModelAttribute("threadForm") ThreadForm form,
	        BindingResult bindingResult,
	        Model model,
	        HttpSession session) {

	    // ログインユーザーを取得
	    LoginUserForm loginUser =
	            (LoginUserForm) session.getAttribute(LOGIN_USER);

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
	     * を使用する。
	     *
	     * エラーの場合は、
	     * 投稿詳細画面を再表示するために
	     * 投稿詳細情報とコメント一覧を取得する。
	     */
	    if (bindingResult.hasErrors()) {

	        // 投稿詳細画面に必要な情報を取得
	    	PostDetailViewData viewData =
	    	        postDetailViewService.createPostDetailViewData(
	    	        		form.getPostId(),
	    	                loginUser);

	        // 投稿詳細情報をModelへ設定
	        model.addAttribute(
	                POST_DETAIL_FORM,
	                viewData.getPostDetailForm());

	        // コメント一覧をModelへ設定
	        model.addAttribute(
	                "threadList",
	                viewData.getThreadList());

	        // ログインフォームをModelへ設定
	        model.addAttribute(
	                LOGIN_FORM,
	                new LoginUserForm());

	        /*
	         * バリデーションエラーとなったThreadFormを
	         * そのままModelへ戻す。
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
	    Integer userId = loginUser.getUserId();

	    // コメント登録
	    commentService.insertComment(
	            form,
	            userId);

	    // 登録成功時は投稿詳細へリダイレクト
	    return POST_DETAIL_REDIRECT + form.getPostId();
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
	@PostMapping(COMMENT_UPDATE)
	public String updateComment(
			@Valid @ModelAttribute("threadForm") ThreadForm form,
			BindingResult bindingResult,
			HttpSession session) {

		// ログインユーザーを取得
		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(
				SessionKeyword.LOGIN_USER);

		/*
		 * 未ログインの場合
		 *
		 * 編集処理を行わず投稿詳細へ戻る。
		 */
		if (loginUser == null) {

			return POST_DETAIL_REDIRECT + form.getPostId();
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

			return POST_DETAIL_REDIRECT + form.getPostId();
		}

		// ログインユーザーIDを取得
		Integer userId = loginUser.getUserId();

		// コメント更新
		commentService.updateComment(
				form,
				userId);

		// 更新成功時は投稿詳細画面へ戻る
		return POST_DETAIL_REDIRECT + form.getPostId();
	}

	/**
	 * コメント削除処理
	 *
	 * @param threadId コメントID
	 * @param postId 投稿ID
	 * @param session ログインユーザー情報取得用
	 * @return 投稿詳細画面へリダイレクト
	 */
	@PostMapping(COMMENT_DELETE)
	public String deleteComment(
			@RequestParam Integer threadId,
			@RequestParam Integer postId,
			HttpSession session) {

		// ログインユーザーを取得
		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(LOGIN_USER);

		// ログインユーザーIDを取得
		Integer userId = loginUser.getUserId();

		// コメントを削除
		commentService.deleteComment(
				threadId,
				userId);

		// 投稿詳細画面へ戻る
		return POST_DETAIL_REDIRECT + postId;
	}
}
