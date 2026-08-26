package com.example.demo.presentation.controller;

import java.util.Collection;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.SearchPostByUserTypeService;
import com.example.demo.domain.service.post.PostService;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final PostService postService;
    private final SearchPostByUserTypeService searchPostByUserType;

    @GetMapping(TransitionTargetPageNameKeyword.RETURN_MENU)
    public String showMenu(
            @RequestParam(required = false, name = "tag")
            Collection<String> tagNames,
            Model model,
            HttpSession session) {

        // セッションからログインユーザー情報を取得
        LoginUserForm loginUserForm =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER
                );

        // ユーザーIDを取得
        Integer userId = loginUserForm == null
                ? null
                : loginUserForm.getUserId();

        // Serviceで投稿を取得
        model.addAttribute(
                "posts",
                searchPostByUserType.searchPostByUserType(
                        userId,
                        tagNames
                ));

     // タグ一覧を取得
        if (userId != null && loginUserForm.isArtisan()) {

            // 職人に紐づいているタグだけ取得
            model.addAttribute(
                    "tags",
                    searchPostByUserType.getArtisanTagNames(userId));

        } else {

            // Guest / Customer は全タグ
            model.addAttribute(
                    "tags",
                    postService.getAllTags());
        }
        // 選択中のタグ
        String selectedTag =
                tagNames == null || tagNames.isEmpty()
                        ? null
                        : tagNames.iterator().next();

        model.addAttribute("selectedTag", selectedTag);
        
        
        // 共通メニュー画面へ
        return TransitionTargetPageNameKeyword.MENU_HTML;
    }
}