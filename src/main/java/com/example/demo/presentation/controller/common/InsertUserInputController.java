package com.example.demo.presentation.controller.common;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.RegisterUserService;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.common.InsertUserForm;

/*
 * 新規登録画面表示用コントローラ
 */

@Controller
public class InsertUserInputController {
	//	ログ出力用
	private final static Logger LOGGER = Logger.getLogger(InsertUserInputController.class.getName());
	// このインスタンスは使用していない（EMでも同様）が、処理共通serviceのインスタンスは関連するコントローラに必ず作成するなど
	// コーディング上のルールか何かで存在する物と思われる（要確認）
	RegisterUserService register;

	InsertUserInputController(RegisterUserService register){
		this.register = register;
	}

	// おそらく不要
	@ModelAttribute
	public InsertUserForm insertUserForm() {
		return new InsertUserForm();
	}
	// 登録画面表示
	@PermissionCheck
	@GetMapping(TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT)
	public String insertInput(InsertUserForm insertUserForm, Model model){
		model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertUserForm);
		return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
	}
	@PermissionCheck
	@PostMapping(TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT)
	public String returnInsertInput(InsertUserForm insertUserForm, Model model){
		model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertUserForm);
		return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
	}
}
