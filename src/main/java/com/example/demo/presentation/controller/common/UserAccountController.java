package com.example.demo.presentation.controller.common;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.SearchCustomerService;
import com.example.demo.domain.service.post.PostService;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.common.UserAccountEditForm;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class UserAccountController {

	private final PostService postService;
	private final SearchCustomerService searchCustomerService;
	@PermissionCheck
	@GetMapping(TransitionTargetPageNameKeyword.ACCOUNT)
	public String userAccount(Model model, HttpSession session) {
		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
		Integer userId = loginUser.getUserId();

		UserAccountEditForm userAccountEditForm = searchCustomerService.searchIdCustomer(userId);
		String name = userAccountEditForm.getUserName();
		String mail = userAccountEditForm.getEmail();

		model.addAttribute(PageReturnAttributeKeyword.USER_NAME, name);
		model.addAttribute(PageReturnAttributeKeyword.EMAIL, mail);

		List<PostEntity> posts = postService.findByUserIdAndIsDeleted(userId, (byte) 0);
		model.addAttribute(PageReturnAttributeKeyword.POSTS, postService.convertToPostListForm(posts));

		return TransitionTargetPageNameKeyword.ACCOUNT_HTML;
	}
}
