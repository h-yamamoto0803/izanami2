
package com.example.demo.domain.service.common;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.common.LoginUserForm;

import lombok.RequiredArgsConstructor;

/**
 * ログインに関する演算処理を担当するServiceです。
 *
 * Controllerでは画面からの入力値を受け取ったり、
 * ログイン後の画面へ遷移したりすることを担当します。
 *
 * 一方、このServiceでは、
 * 「入力されたメールアドレス・パスワードが正しいか」
 * 「削除されたユーザーではないか」
 * といった業務上の判定を担当します。
 */
@Service
@RequiredArgsConstructor
public class LoginService {

	private final UserRepository userRepository;

	/**
	 * ログイン認証を行います。
	 *
	 * @param loginUserForm ログイン画面から入力された情報
	 * @return ログイン成功の場合true、失敗の場合false
	 */
	public boolean doLogin(LoginUserForm loginUserForm) {

	    Optional<UserEntity> optionalUser =
	            userRepository.findByEmail(loginUserForm.getEmail());

	    if (optionalUser.isEmpty()) {
	        return false;
	    }

	    UserEntity user = optionalUser.get();

	    // 削除済みユーザーの場合
	    if (user.getIsDeleted().equals(UserEntity.DELETED)) {
	        return false;
	    }

	    // メールアドレスまたはパスワードが一致しない場合
	    if (!loginUserForm.getEmail().equals(user.getEmail())
	            || !loginUserForm.getPassword().equals(user.getPassword())) {
	        return false;
	    }

	    // ログインユーザーの情報を設定
	    loginUserForm.setUserId(user.getUserId());
	    loginUserForm.setUserType(user.getUserType());
	    loginUserForm.setUserName(user.getUserName());

	    return true;
	}
}

