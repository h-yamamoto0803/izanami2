package com.example.demo.presentation.controller;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.post.PostService;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final PostService postService;

@GetMapping({ INDEX_BLANK, INDEX_SLASH, MENU_HTML })
    public String showMenu(
            @RequestParam(required = false, name = "tag")
            String selectedTag,
            Model model,
            HttpSession session) {

        // セッションからログインユーザー情報を取得
        LoginUserForm loginUserForm =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER
                );

        Integer userId = null;

     // ログインユーザーが存在する場合はユーザーIDを取得
     if (loginUserForm != null) {
         userId = loginUserForm.getUserId();
     }

        // Serviceで投稿を取得
        model.addAttribute("posts",postService.searchPostByUserType(userId,selectedTag));

        // タグ一覧を取得
        if (userId != null && loginUserForm.isArtisan()) {

            // Artisanに紐づいているタグだけ取得
            model.addAttribute("tags",postService.getArtisanTagNames(userId));
        } else {
            // Guest / Customerは全タグ
            model.addAttribute("tags",postService.getAllTags());
        }

        // 選択中のタグ
        model.addAttribute("selectedTag", selectedTag);

        // 共通メニュー画面へ
        return TransitionTargetPageNameKeyword.MENU_HTML;
    }
}