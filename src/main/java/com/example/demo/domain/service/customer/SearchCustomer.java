package com.example.demo.domain.service.customer;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.common.UserAccountEditForm;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class SearchCustomer {

	
private final UserRepository userRepository;
	
public UserAccountEditForm searchIdCustomer(Integer userId) {
		
		UserAccountEditForm userAccountEditForm = new UserAccountEditForm();
		UserEntity userEntity = userRepository.findById(userId).orElseThrow();
		
		
		//userIdでDBからsearchする
		//とったUserEntityから、FormにSetする
		userAccountEditForm.setUserId(userEntity.getUserId());
		userAccountEditForm.setUserType(userEntity.getUserType());
		userAccountEditForm.setUserName(userEntity.getUserName());
		userAccountEditForm.setEmail(userEntity.getEmail());
		userAccountEditForm.setPassword(userEntity.getPassword());

		return userAccountEditForm;
	}

	
	
	}
