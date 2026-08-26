package com.example.demo.presentation.controller;

import java.util.Collection;

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

	@GetMapping(TransitionTargetPageNameKeyword.RETURN_MENU)
	public String showMenu(

			@RequestParam(required = false, name = "tag") Collection<String> tagNames,
			Model model,
			HttpSession session) {

		model.addAttribute(
				"posts",
				postService.getPostListFromDatabase(tagNames));

		model.addAttribute(
				"tags",
				postService.getAllTags());
		
		 String selectedTag =
		            tagNames == null || tagNames.isEmpty()
		                    ? null
		                    : tagNames.iterator().next();
		
		model.addAttribute("selectedTag",selectedTag);
		
		LoginUserForm loginUserForm =
                (LoginUserForm) session.getAttribute(
                        SessionKeyword.LOGIN_USER
                );

        // 未ログイン
        if (loginUserForm == null) {
            model.addAttribute("userType", "guest");

            return TransitionTargetPageNameKeyword.MENU_HTML;
        }

        // Artisan
        if (loginUserForm.isArtisan()) {
            model.addAttribute("userType", "artisan");

            return TransitionTargetPageNameKeyword.ARTISAN_MENU_HTML;
        }

        // Customer
        if (loginUserForm.isCustomer()) {
            model.addAttribute("userType", "customer");

            return TransitionTargetPageNameKeyword.CUSTOMER_MENU_HTML;
        }

        // 想定外のユーザータイプ
        return TransitionTargetPageNameKeyword.MENU_HTML;
    }
}
	