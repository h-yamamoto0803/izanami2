package com.example.demo.presentation.controller.common;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.SearchUser;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.common.InsertUserForm;
import com.example.demo.presentation.form.common.MessageForm;

import lombok.RequiredArgsConstructor;

/*
 * 新規登録入力内容確認用コントローラ
 */
@RequiredArgsConstructor
@Controller
public class InsertUserConfirmController {

	private final SearchUser searchUser;

	

	@ModelAttribute
	public InsertUserForm insertUserForm(){
		return new InsertUserForm();
	}

	// 登録情報チェック
	@PermissionCheck
	@PostMapping(INSERT_CUSTOMER_CONFIRM)
	public String insertUserConfirm(InsertUserForm insertUserForm, Model model){
		List<String> error = insertUserForm.validateParameter();

		if (!error.isEmpty()){
			// 誤りがあった場合
			// 誤りがあった箇所のエラーメッセージを表示
			model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR,
			// Listの内容を一つの文字列に変更
			new MessageForm(String.join(", ", error) + "が不正です。"));
			// 画面に返す為の登録情報をセット
			model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertUserForm);
			// 登録画面に遷移
			return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
		}
		
		//　メールアドレス重複チェック
		final String DUPLICATION = "既に登録済みのメールアドレスです。";
		UserEntity otherUser = searchUser.searchUserByEmail(insertUserForm.getEmail());

		// 検索結果があるか
		if (otherUser != null){
			// 検索した結果0件ではない場合
			// 重複を許さないため、エラー処理
			model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR,
					new MessageForm(DUPLICATION));
			// 画面に返す為の登録情報をセット
			model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertUserForm);
			// 登録画面に遷移
			return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
		}

		model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertUserForm);
		// 登録確認画面に遷移
		return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_CONFIRM_HTML;
	}
}