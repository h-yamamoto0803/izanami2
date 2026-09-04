package com.example.demo.presentation.controller.post;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.post.DeletePost;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeletePostController {

    private final DeletePost deletePost;
    
    @PermissionCheck
    @GetMapping(TransitionTargetPageNameKeyword.DELETE_POST)
    public String deletePost(@RequestParam Integer postId, 
    		RedirectAttributes redirect,
    		HttpSession session){
    	
    	LoginUserForm loginUserForm = (LoginUserForm)session.getAttribute(SessionKeyword.LOGIN_USER);
    	Integer userId = loginUserForm.getUserId();
        String deleteMessage = deletePost.deletePost(postId, userId);
        redirect.addFlashAttribute(PageReturnAttributeKeyword.DELETE_MESSAGE, deleteMessage);

        return "redirect:/account";
    }
}
