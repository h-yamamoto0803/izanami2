package com.example.demo.presentation.controller.common;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.UpdateUserAccount;
import com.example.demo.domain.service.customer.SearchCustomer;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.common.UserAccountEditForm;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class DoEditUserAccountController {

	private final UpdateUserAccount updateUserAccount;
	private final SearchCustomer searchCustomer;

	
	@PermissionCheck
	@PostMapping(DO_EDIT_ACCOUNT)
	public String doEditUserAccount(Model model,
			@ModelAttribute UserAccountEditForm userAccountEditForm,
			HttpSession session) {
		try {
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);

			UserAccountEditForm beforecustomerAccountEditForm = searchCustomer.searchIdCustomer(loginUser.getUserId());
			userAccountEditForm.setUserId(beforecustomerAccountEditForm.getUserId());
			userAccountEditForm.setUserType(beforecustomerAccountEditForm.getUserType());

			UserEntity updateUserEntity = UserAccountEditForm.convertTo(userAccountEditForm);
			updateUserAccount.updateUser(beforecustomerAccountEditForm,updateUserEntity);
			
			loginUser.setUserName(updateUserEntity.getUserName());
			session.setAttribute(SessionKeyword.LOGIN_USER, loginUser);
			return REDIRECT_MENU;
		} catch (Exception e) {
			
			// ログイン情報の破棄
			session.invalidate();
			e.printStackTrace();
			model.addAttribute(MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			return REDIRECT_MENU;
		}
	}
}