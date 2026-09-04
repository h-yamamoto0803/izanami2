package com.example.demo.presentation.controller.common;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.SearchUser;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.common.UserAccountEditForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ConfirmUserAccountEditController {

	private final SearchUser searchUser;
     
	@PermissionCheck
	@PostMapping(CONFIRM_ACCOUNT_EDIT)
	public String confirmUserAccountEdit(
			@Valid UserAccountEditForm userAccountEditForm,
			BindingResult bindingResult,
			Model model,
			HttpSession session){

		try{
			//Formのバリデーションではじかれたとき
			if(bindingResult.hasErrors()){
				return ACCOUNT_EDIT_HTML;
		}

			//パスワードが確認用と違くてはじかれたとき
			final String ERROR = "パスワードが一致していません";
			String password = userAccountEditForm.getPassword();
			String passwordConfirm = userAccountEditForm.getPasswordConfirm();
			String passwordError = userAccountEditForm.validatePassword(password, passwordConfirm);

			if (passwordError.equals(ERROR)) {
				model.addAttribute(USER_ACCOUNT_EDIT_FORM, userAccountEditForm);
				model.addAttribute(MESSAGE_ERROR, ERROR);
				return ACCOUNT_EDIT_HTML;
			}

			//既に登録済みのメールアドレスを入力してはじかれたとき
			final String DUPLICATION = "既に登録済みのメールアドレスです。";
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);

			String beforeUsermail = loginUser.getEmail();

			if (!beforeUsermail.equals(userAccountEditForm.getEmail())) {
			    UserEntity userByEmail =
			            searchUser.searchUserByEmail(userAccountEditForm.getEmail());

			    if (userByEmail != null) {
			        model.addAttribute(USER_ACCOUNT_EDIT_FORM, userAccountEditForm);
			        model.addAttribute(MESSAGE_ERROR, DUPLICATION);
			        return ACCOUNT_EDIT_HTML;
			    }
		

				// 登録確認画面に遷移
				model.addAttribute(USER_ACCOUNT_EDIT_FORM, userAccountEditForm);
				return ACCOUNT_EDIT_CONFIRM_HTML;
			}

			// 登録確認画面に遷移
			model.addAttribute(USER_ACCOUNT_EDIT_FORM, userAccountEditForm);
			return ACCOUNT_EDIT_CONFIRM_HTML;

		}catch (Exception e){
			// ログイン情報の破棄
			session.invalidate();
			e.printStackTrace();
			model.addAttribute(MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			// エラー画面遷移
			return MENU_HTML;
		}
	}
}