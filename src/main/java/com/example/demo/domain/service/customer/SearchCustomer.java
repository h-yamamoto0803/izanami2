package com.example.demo.domain.service.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

public class SearchCustomer {
UserRepository userRepository;
	
	@Autowired
	public SearchCustomer(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}
public CustomerAccountEditForm searchIdCustomer(Integer userId) {
		
		CustomerAccountEditForm customerAccountEditForm = new CustomerAccountEditForm();
		UserEntity userEntity = userRepository.findById(userId).orElseThrow();
		
		
		//userIdでDBからsearchする
		//とったUserEntityから、FormにSetする
		customerAccountEditForm.setUserName(userEntity.getUserName());
		customerAccountEditForm.setEmail(userEntity.getEmail());
		customerAccountEditForm.setPassword(userEntity.getPassword());

		return customerAccountEditForm;
	}

	
	public List<UserEntity> searchUserByEmail(String email) {
		Optional<UserEntity>userOptional =userRepository.findByEmail(email);
		if(userOptional.isEmpty()) {
		return List.of();}else {
			return List.of(userOptional.get());
		}
	}
}
