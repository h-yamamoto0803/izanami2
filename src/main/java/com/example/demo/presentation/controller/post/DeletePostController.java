package com.example.demo.presentation.controller.post;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.post.DeletePost;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeletePostController {

    private final DeletePost deletePost;
    private final HttpSession httpSession;

    @GetMapping(TransitionTargetPageNameKeyword.DELETE_POST)
    public String deletePost(@RequestParam Integer postId, 
    		@RequestParam Integer userId,
    		Model model) {
    	
        String deleteMessage = deletePost.deletePost(postId, userId);
        model.addAttribute("deleteMessage", deleteMessage);

        return TransitionTargetPageNameKeyword.REDIRECT
       		 +TransitionTargetPageNameKeyword.RETURN_MENU;
    }
}
