package com.example.demo.domain.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.service.customer.UpdateCustomerAccountTest;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Service
@Transactional
public class UpdateCustomerAccountTestImpl implements UpdateCustomerAccountTest{
	
	
@Override
	public CustomerAccountEditForm searchIdCustomer(Integer userId) {
		CustomerAccountEditForm customerAccountEditForm = new CustomerAccountEditForm();
		
		customerAccountEditForm.setUserName("試太郎");
		customerAccountEditForm.setEmail("try@try");
		customerAccountEditForm.setPassword("trytry");
		customerAccountEditForm.setPasswordConfirm("trytry");
		
        return customerAccountEditForm ;
    }
	
	
@Override
	public CustomerAccountEditForm searchEmailCustomer(String email) {
CustomerAccountEditForm customerAccountEditForm = new CustomerAccountEditForm();
		
		customerAccountEditForm.setUserName("試太郎");
		customerAccountEditForm.setEmail("try@try");
		customerAccountEditForm.setPassword("trytry");
		customerAccountEditForm.setPasswordConfirm("trytry");
		
        return customerAccountEditForm ;
        
	}

}
