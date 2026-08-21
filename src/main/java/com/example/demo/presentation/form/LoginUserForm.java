package com.example.demo.presentation.form;

/**
 * ログイン画面で入力された情報を保持するFormです。
 *
 * Artisan・Customerの両方のログイン処理から使用します。
 */
public class LoginUserForm {

	/** メールアドレス */
	private String email;

	/** パスワード */
	private String password;

	/**
	 * メールアドレスを取得します。
	 *
	 * @return メールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレスを設定します。
	 *
	 * @param email メールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * パスワードを取得します。
	 *
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定します。
	 *
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isArtisan() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean isCustomer() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}