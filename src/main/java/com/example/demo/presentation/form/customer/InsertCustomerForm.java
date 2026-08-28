package com.example.demo.presentation.form.customer;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.example.demo.infra.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertCustomerForm {

	private String userName;

	@NotBlank
	@Email
	private String email;

	private String password;

	private String passwordConfirm;

	/**
	 * パスワードのチェック
	 * チェック対象にエラーが有った場合エラーリストに追加する
	 * @param password        登録対象のパスワード
	 * @param passwordConfirm 登録対象のパスワード再入力
	 * @param error           エラーリスト
	 */
	private static void checkPassword(String password, String passwordConfirm, List<String> error) {
		final String PASSWORD_NULL_BLANK = "パスワード が未入力です。";
		final String PASSWORD_CONFIRM_NULL_BLANK = "パスワード確認用 が未入力です。";
		final String PASSWORD_MISMATCH = "パスワード と パスワード確認用 が一致しません。";

		if (isNullOrBlank(password)) {
			error.add(PASSWORD_NULL_BLANK);
			return;
		}
		if (isNullOrBlank(passwordConfirm)) {
			error.add(PASSWORD_CONFIRM_NULL_BLANK);
			return;
		}

		if (!password.equals(passwordConfirm)) {
			error.add(PASSWORD_MISMATCH);
		}
	}

	/**
	 * 文字列のnull、空白文字判定
	 */
	private static boolean isNullOrBlank(String str) {
		if (null == str) {
			return true;
		}

		return str.isBlank();
	}

	/**
	 * 画面から渡ってきた登録内容をまとめ、entityに変換
	 *
	 * @param insertEmployeeForm 画面から渡ってきた登録内容
	 * @return UserEntityインスタンス
	 */
	public static UserEntity convertTo(InsertCustomerForm insertEmployeeForm) {
		UserEntity userEntity = new UserEntity();

		userEntity.setUserName(insertEmployeeForm.getUserName());
		userEntity.setEmail(insertEmployeeForm.getEmail());
		userEntity.setPassword(insertEmployeeForm.getPassword());
		return userEntity;

	}

	/**
	 * 登録データのValidationを行う
	 *
	 * @return エラーが有った場合はエラー文言、無ければ空のリストが返る
	 */
	public List<String> validateParameter() {
		final String EMPLOYEE_NAME_NULL_BLANK = "ユーザー名";
		final String MAIL_ADDRESS_NULL_BLANK = "メールアドレス";

		List<String> error = new ArrayList<>();

		if (isNullOrBlank(userName)) {
			error.add(EMPLOYEE_NAME_NULL_BLANK);
		}
		if (isNullOrBlank(email)) {
			error.add(MAIL_ADDRESS_NULL_BLANK);
		}

		// パスワードが仕様を満たしているか確認
		checkPassword(password, passwordConfirm, error);

		return error;

	}
}
