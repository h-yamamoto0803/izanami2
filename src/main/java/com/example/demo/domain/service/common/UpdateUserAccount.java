package com.example.demo.domain.service.common;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.common.UserAccountEditForm;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UpdateUserAccount {
	
	private final UserRepository userRepository;

	public void updateUser(UserAccountEditForm beforecustomerAccountEditForm, UserEntity updateUser) {
		userRepository.save(updateUser);
	}

}
