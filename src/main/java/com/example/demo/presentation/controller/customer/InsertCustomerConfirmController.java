package com.example.demo.presentation.controller.customer;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.RegisterCustomer;
import com.example.demo.domain.service.customer.SearchUser;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.MessageForm;
import com.example.demo.presentation.form.customer.InsertCustomerForm;

/*
 * 新規登録入力内容確認用コントローラ
 */
@Controller
public class InsertCustomerConfirmController {

	HttpSession httpSession;
	RegisterCustomer register;
	SearchUser searchUser;

	public InsertCustomerConfirmController(HttpSession httpSession,
			RegisterCustomer register,
			SearchUser searchUser){
		this.httpSession = httpSession;
		this.register = register;
		this.searchUser = searchUser;
	}

	@ModelAttribute
	public InsertCustomerForm insertCustomerForm(){
		return new InsertCustomerForm();
	}

	// 登録情報チェック
	@PermissionCheck
	@PostMapping(INSERT_CUSTOMER_CONFIRM)
	public String insertCustomerConfirm(InsertCustomerForm insertCustomerForm, Model model){
		List<String> error = insertCustomerForm.validateParameter();

		if (!error.isEmpty()){
			// 誤りがあった場合
			// 誤りがあった箇所のエラーメッセージを表示
			model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR,
			// Listの内容を一つの文字列に変更
			new MessageForm(String.join(", ", error) + "が不正です。"));
			// 画面に返す為の登録情報をセット
			model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertCustomerForm);
			// 登録画面に遷移
			return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
		}
		
		//　メールアドレス重複チェック
		final String DUPLICATION = "既に登録済みのメールアドレスです。";
		UserEntity otherUser = searchUser.searchUser(insertCustomerForm.getEmail());

		// 検索結果があるか
		if (otherUser != null){
			// 検索した結果0件ではない場合
			// 重複を許さないため、エラー処理
			model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR,
					new MessageForm(DUPLICATION));
			// 画面に返す為の登録情報をセット
			model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertCustomerForm);
			// 登録画面に遷移
			return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
		}

		model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertCustomerForm);
		// 登録確認画面に遷移
		return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_CONFIRM_HTML;
	}
}