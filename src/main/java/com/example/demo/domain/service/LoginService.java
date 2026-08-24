package com.example.demo.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.LoginUserForm;

import lombok.RequiredArgsConstructor;

/**
 * ログインに関する演算処理を担当するServiceです。
 *
 * Controllerでは画面からの入力値を受け取ったり、
 * ログイン後の画面へ遷移したりすることを担当します。
 *
 * 一方、このServiceでは、
 * 「入力されたメールアドレス・パスワードが正しいか」
 * 「ログインしようとしているユーザー種別が正しいか」
 * といった業務上の判定を担当します。
 */
@Service
@RequiredArgsConstructor

public class LoginService {
	private final UserRepository userRepository;
	
	    
	public Integer doLogin(LoginUserForm form, Integer userType) {
	    Optional<UserEntity> optionalUser =
	            userRepository.findByEmail(form.getEmail());
	    if (optionalUser.isEmpty()) {
	        return 0;
	    }

	    UserEntity user = optionalUser.get();

	    if (user.getIsDeleted() != null && user.getIsDeleted() == 1) {
            return null;
        }
	    
		if (form.getEmail().equals(user.getEmail())
		        && form.getPassword().equals(user.getPassword())
		        && userType.equals(user.getUserType())) {

		    return 1;
		}
		
			return 0;
		}
	}
    