package com.example.demo.presentation.controller.customer;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	UpdateCustomerAccount updateCustomerAccount;
	

	@Autowired
	public CustomerAccountController(HttpSession httpsession, 
			UpdateCustomerAccount updateCustomerAccount,
			PostService postService) {
		this.httpSession = httpsession;
		this.updateCustomerAccount = updateCustomerAccount;
		this.postService = postService;
		

	}

	@RequestMapping(value = TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT, method = RequestMethod.GET)
	public String customerAccount(Model model, HttpSession session) {

		//後で消す
		LoginUserForm loginUserForm = new LoginUserForm();
		loginUserForm.setUserId(1);
		session.setAttribute(
				SessionKeyword.LOGIN_USER,
				loginUserForm);
		//ここまで

		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
		Integer userId = loginUser.getUserId();
		
		CustomerAccountEditForm customerAccountEditForm = updateCustomerAccount.searchIdCustomer(userId);
		String name = customerAccountEditForm.getUserName();
		String mail = customerAccountEditForm.getEmail();

		model.addAttribute("userName", name);
		model.addAttribute("email", mail);
		
		
		List<PostEntity> posts = postService.findByUserId(userId);
		System.out.println(posts);		
		System.out.println(postService.convertToPostListForm(posts));
		model.addAttribute("posts", postService.convertToPostListForm(posts));

		return TransitionTargetPageNameKeyword.CUSTOMER_ACCOUNT_HTML;
	}
}
