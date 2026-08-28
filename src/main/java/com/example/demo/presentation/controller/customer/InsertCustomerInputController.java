package com.example.demo.presentation.controller.customer;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.RegisterCustomer;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.customer.InsertCustomerForm;

/*
 * 新規登録画面表示用コントローラ
 */

@Controller
public class InsertCustomerInputController {
	//	ログ出力用
	private final static Logger LOGGER = Logger.getLogger(InsertCustomerInputController.class.getName());
	// このインスタンスは使用していない（EMでも同様）が、処理共通serviceのインスタンスは関連するコントローラに必ず作成するなど
	// コーディング上のルールか何かで存在する物と思われる（要確認）
	RegisterCustomer register;

	InsertCustomerInputController(RegisterCustomer register) {
		this.register = register;
	}

	// おそらく不要
	@ModelAttribute
	public InsertCustomerForm insertCustomerForm() {
		return new InsertCustomerForm();
	}
	// 登録画面表示
	@PermissionCheck
	@GetMapping(TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT)
	public String insertInput(InsertCustomerForm insertCustomerForm, Model model) {
		model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertCustomerForm);
		return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
	}
	@PermissionCheck
	@PostMapping(TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT)
	public String returnInsertInput(InsertCustomerForm insertCustomerForm, Model model) {
		model.addAttribute(PageReturnAttributeKeyword.INSERT_CUSTOMER_FORM, insertCustomerForm);
		return TransitionTargetPageNameKeyword.INSERT_CUSTOMER_INPUT_HTML;
	}
}
