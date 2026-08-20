package com.example.demo.presentation.form;

import com.example.demo.infra.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ログインユーザーの情報を管理するform
 * Entityへの変換メソッドもこちらに属する
 * ユーザータイプの判定メソッド等未実装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserForm {

	private Integer userId;
	private Byte userType;
	private String userName;
	private String email;
	private String password;

	public boolean isArtisan() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean isCustomer() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
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
