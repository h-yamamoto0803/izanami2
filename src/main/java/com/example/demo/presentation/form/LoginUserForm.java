package com.example.demo.presentation.form;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.example.demo.infra.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ログインユーザーの情報を管理するform
 * Entityへの変換メソッドもこちらに属する
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserForm {

	private Integer userId;
	private Byte userType;
	private String userName;
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "正しいメールアドレスを入力してください")
	private String email;
	@NotBlank(message = "パスワードを入力してください")
	private String password;

	/**
	 * userTypeの値が判定用メソッドが指定する値と一致するか判定
	 * 消費者:1, 職人:2
	 * @return: 指定された種類のユーザーと一致すればtrue
	 */

/**
 * ログイン画面で入力された情報を保持するFormです。
 *
 * Artisan・Customerの両方のログイン処理から使用します。
 */

	public boolean isArtisan() {
		return getUserType() == 2;
	}

	public boolean isCustomer() {
		return getUserType() == 1;
	}


	/**
	 * ログインユーザーの情報をユーザーEntityに変換して返す
	 * @param LoginUserForm
	 * @return UserEntity
	 */
	public UserEntity convertToUserEntity(LoginUserForm form) {
		UserEntity user = new UserEntity();

		user.setUserId(form.getUserId());
		user.setUserType(form.getUserType());
		user.setUserName(form.getUserName());
		user.setEmail(form.getEmail());
		user.setPassword(form.getPassword());

		return user;
	}
}



