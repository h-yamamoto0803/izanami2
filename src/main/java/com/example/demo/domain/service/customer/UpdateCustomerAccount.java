package com.example.demo.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;
@Service

public class UpdateCustomerAccount {
	
	
	UserRepository userRepository;
	
	@Autowired
	public UpdateCustomerAccount(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}
	

	public void updateCustomer(CustomerAccountEditForm beforecustomerAccountEditForm, UserEntity updateUser) {
		userRepository.save(updateUser);
	}

}
