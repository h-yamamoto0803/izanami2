package com.example.demo.domain.service.common;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;

@Service
public class SearchUserService {

	UserRepository repository;

	public SearchUserService(UserRepository repository) {
		this.repository = repository;
	}

	public UserEntity searchUserByEmail(String email) {
	    return repository.findByEmail(email).orElse(null);
	}
}