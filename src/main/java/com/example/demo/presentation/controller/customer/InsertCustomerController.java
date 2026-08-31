package com.example.demo.presentation.controller.customer;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.RegisterCustomer;
import com.example.demo.domain.service.customer.SearchUser;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.form.customer.InsertCustomerForm;

/*
 * 登録処理用コントローラ
 * 遷移はbooleanを返してJS側で処理
 */
@Controller
public class InsertCustomerController {
	private final static Logger LOGGER = Logger.getLogger(InsertCustomerController.class.getName());
	// エラーメッセージ用の定数フィールド
	private final static String SEVERE_ERROR_MESSAGE = "エラーが発生しました！";

	HttpSession httpSession;
	RegisterCustomer register;
	SearchUser searchUser;

	public InsertCustomerController(HttpSession httpSession,
			RegisterCustomer register,
			SearchUser searchUser) {
		this.httpSession = httpSession;
		this.register = register;
		this.searchUser = searchUser;
	}

	@ModelAttribute
	public InsertCustomerForm insertCustomerForm() {
		return new InsertCustomerForm();
	}

	@PermissionCheck
	@ResponseBody
	@PostMapping(INSERT_CUSTOMER)
	public Boolean insertCustomer(@Valid @ModelAttribute InsertCustomerForm insertCustomerForm,
			BindingResult bindingResult,
			Model model) {

		// 登録情報のバリデーションチェック
		try {
			List<String> error = validateParameter(
					insertCustomerForm.getUserName(),
					insertCustomerForm.getEmail(),
					insertCustomerForm.getPassword());

			// エラーが1件以上有る場合
			if (!error.isEmpty()) {
				// エラー内容を画面に表示未実装
				// booleanで対応するかどうか
				//model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR,
				//new MessageForm(String.join(", ", error) + "が不正です。"));
				// 登録画面に遷移
				return false;
			}

			if (bindingResult.hasErrors()) {
				return false;
			}

			// メールアドレス重複チェック
			UserEntity otherUser = searchUser.searchUser(insertCustomerForm.getEmail());

			// 検索結果があるか
			if (otherUser != null) {
				// 検索した結果0件ではない場合
				// 重複を許さないため、false
				return false;
			}

			// Entityに変換し登録処理呼び出し
			UserEntity user = InsertCustomerForm.convertTo(insertCustomerForm);
			user.setUserType((byte) 1);
			register.registerCustomer(user);
			// 登録結果画面に遷移
			return true;
		} catch (Exception e) {
			// セッション情報の破棄
			httpSession.invalidate();
			e.printStackTrace();
			// エラーログ出力
			LOGGER.log(Level.SEVERE, SEVERE_ERROR_MESSAGE, e);
			return false;
		}
	}

	// パラメータチェック
	private List<String> validateParameter(
			String userName,
			String email,
			String password) {
		final String USER_NAME_NULL_BLANK = "ユーザー名";
		final String MAIL_ADDRESS_NULL_BLANK = "メールアドレス";
		final String PASSWORD_NULL_BLANK = "パスワード";
		List<String> error = new ArrayList<>();

		// null or 空文字判定
		if (isNullOrBlank(userName)){
			// エラー内容を追加
			error.add(USER_NAME_NULL_BLANK);
		}

		// null or 空文字判定
		if (isNullOrBlank(email)){
			// エラー内容を追加
			error.add(MAIL_ADDRESS_NULL_BLANK);
		}

		// null or 空文字判定
		if (isNullOrBlank(password)){
			// エラー内容を追加
			error.add(PASSWORD_NULL_BLANK);
		}

		return error;
	}

	private static boolean isNullOrBlank(String str){
		if (null == str) {
			return true;
		}

		return str.isBlank();
	}
}
