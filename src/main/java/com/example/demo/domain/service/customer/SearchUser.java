package com.example.demo.domain.service.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;

@Service
public class SearchUser {

	UserRepository repository;

	public SearchUser(UserRepository repository) {
		this.repository = repository;
	}

	public List<UserEntity> searchUser(String email) {
		return repository.findByEmail(email);
	}
}