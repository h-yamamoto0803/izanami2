package com.example.demo.presentation.controller.customer;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.domain.service.customer.UpdateCustomerAccount;
import com.example.demo.domain.service.post.PostService;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class CustomerAccountController {

	HttpSession httpSession;
	PostService postService;
	SearchCustomer searchCustomer;
	UpdateCustomerAccount updateCustomerAccount;
	
	public CustomerAccountController(HttpSession httpsession, 
			UpdateCustomerAccount updateCustomerAccount,
			PostService postService,SearchCustomer searchCustomer) {
		this.httpSession = httpsession;
		this.updateCustomerAccount = updateCustomerAccount;
		this.postService = postService;
		this.searchCustomer = searchCustomer;
	}

	@GetMapping(TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT)
	public String customerAccount(Model model, HttpSession session) {
		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
		Integer userId = loginUser.getUserId();
		
		CustomerAccountEditForm customerAccountEditForm = searchCustomer.searchIdCustomer(userId);
		String name = customerAccountEditForm.getUserName();
		String mail = customerAccountEditForm.getEmail();

		model.addAttribute("userName", name);
		model.addAttribute("email", mail);
		
		List<PostEntity> posts = postService.findByUserIdAndIsDeleted(userId,(byte)0);
		
		model.addAttribute("posts", postService.convertToPostListForm(posts));

		return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_HTML;
	}
}
