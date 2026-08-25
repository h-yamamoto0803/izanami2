package com.example.demo.presentation.controller.artisan;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.post.PostService;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostArtisanController {

    private final PostService postService;
    @PermissionCheck
    @GetMapping(TransitionTargetPageNameKeyword.POST_ARTISAN_CONTROLLER)
    public String showArtisanMenu(
            @RequestParam(required = false) String tag,
            Model model) {

        model.addAttribute(
                "posts",
                postService.getPostListFromDatabase(tag)
        );

        model.addAttribute(
                "tags",
                postService.getAllTags()
        );

        model.addAttribute("userType", "artisan");

        model.addAttribute("selectedTag", tag);

        return TransitionTargetPageNameKeyword.ARTISAN_MENU_HTML;
    }
}