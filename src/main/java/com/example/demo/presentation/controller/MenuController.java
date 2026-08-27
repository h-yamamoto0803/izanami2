package com.example.demo.presentation.controller;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.post.PostService;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.post.PostListForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final PostService postService;

	@GetMapping({ INDEX_BLANK, INDEX_SLASH, MENU_HTML })
	public String showMenu2(
			@RequestParam(required = false, name = "tag") String selectedTag,
			Model model,
			HttpSession session) {

		// セッションからログインユーザー情報を取得
		LoginUserForm loginUserForm = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);

        Integer userId = null;

	     // ログインユーザーが存在する場合はユーザーIDを取得
	     if (loginUserForm != null) {
	         userId = loginUserForm.getUserId();
	     }

		List<PostListForm> postList = postService.searchPostByUserType(userId, selectedTag);

		// Serviceで投稿を取得
		model.addAttribute("posts", postList);

		// タグ一覧を取得
		if (userId != null && loginUserForm.isArtisan()) {

			// 職人に紐づいているタグだけ取得
			model.addAttribute("tags", postService.getArtisanTagNames(userId));
		} else {
			// Guest / Customer は全タグ
			model.addAttribute("tags", postService.getAllTags());
		}
		model.addAttribute("selectedTag", selectedTag);

		// 共通メニュー画面へ
		return TransitionTargetPageNameKeyword.MENU_HTML;
	}
}