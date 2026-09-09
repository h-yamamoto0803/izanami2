package com.example.demo.domain.service.common;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;

/*
 * 
 * 
 */

@Service
public class RegisterUserService {
	UserRepository repository;

	
	public RegisterUserService(UserRepository repository) {
		this.repository = repository;
	}

	public void registerUser(UserEntity userEntity) {
		repository.save(userEntity);
	}
}
