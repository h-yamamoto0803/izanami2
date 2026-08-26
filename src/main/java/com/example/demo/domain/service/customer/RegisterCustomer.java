package com.example.demo.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;

/*
 * 
 * 
 */

@Service
public class RegisterCustomer {
	UserRepository repository;

	@Autowired
	public RegisterCustomer(UserRepository repository) {
		this.repository = repository;
	}

	public void registerCustomer(UserEntity userEntity) {
		repository.save(userEntity);
	}
}
