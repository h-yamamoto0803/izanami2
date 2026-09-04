package com.example.demo.presentation.controller.common;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.DeleteUserAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.common.UserAccountEditForm;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class DeleteUserAccountController {
	private final HttpSession httpSession;
	private final DeleteUserAccount deleteUserAccount;


	
	@PermissionCheck
	@GetMapping(DELETE_ACCOUNT)
	public String deleteUserAccount(Model model,
			@ModelAttribute UserAccountEditForm userAccountEditForm,
    		RedirectAttributes redirect,
			HttpSession session) {

		try {
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
			UserAccountEditForm beforeuserrAccountEditForm = deleteUserAccount.searchIdUser(loginUser.getUserId());
			UserEntity deleteUserEntity = UserAccountEditForm.convertTo(beforeuserrAccountEditForm);

			deleteUserEntity.setIsDeleted((byte) 1);
			deleteUserAccount.deleteUser(beforeuserrAccountEditForm,deleteUserEntity);
			String deleteMessage = "ユーザーの削除が完了しました";
			httpSession.invalidate();
	        redirect.addFlashAttribute(PageReturnAttributeKeyword.DELETE_MESSAGE, deleteMessage);

			return REDIRECT_MENU;

		} catch (Exception e) {
			// ログイン情報の破棄
			httpSession.invalidate();
			e.printStackTrace();
			model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			return REDIRECT_MENU;
		}
	}
}
