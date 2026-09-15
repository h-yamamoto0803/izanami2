package com.example.demo.presentation.controller.common;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.TagService;
import com.example.demo.domain.service.portfolio.PortfolioService;
import com.example.demo.domain.service.post.PostService;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.post.PostListForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final PostService postService;
    private final TagService tagService;
    private final PortfolioService portfolioService;

    @PermissionCheck
    @GetMapping({ INDEX_BLANK, INDEX_SLASH, MENU_HTML })
    public String showMenu(
            @RequestParam(required = false, name = "tag") String selectedTag,
            @RequestParam(required = false, defaultValue = "all")
            String contentType,
            Model model,
            HttpSession session) {

        LoginUserForm loginUserForm =
                (LoginUserForm) session.getAttribute(LOGIN_USER);

        Integer userId = null;

        if (loginUserForm != null) {
            userId = loginUserForm.getUserId();
        }

        List<PostListForm> postList =
                postService.searchPostByUserType(userId, selectedTag);

        model.addAttribute(POSTS, postList);

        // ポートフォリオ一覧
        model.addAttribute(
                "portfolios",
                portfolioService.searchPortfolioByUserType(
                        userId,
                        selectedTag
                )
        );
        // 選択状態保持
        model.addAttribute(
                "contentType",
                contentType
        );

        if (userId != null && loginUserForm.isArtisan()) {
            model.addAttribute(
                    TAGS,
                    tagService.getArtisanTagNames(userId)
            );
        } else {
            model.addAttribute(
                    TAGS,
                    tagService.getAllTags()
            );
        }

        model.addAttribute(
                SELECTED_TAGS,
                selectedTag
        );

        LoginUserForm loginUserFormInput =
                new LoginUserForm();

        model.addAttribute(
                LOGIN_FORM,
                loginUserFormInput
        );

        return MENU_HTML;
    }
}
