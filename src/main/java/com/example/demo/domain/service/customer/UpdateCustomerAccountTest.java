package com.example.demo.domain.service.customer;

import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

public interface UpdateCustomerAccountTest {
	CustomerAccountEditForm searchIdCustomer(Integer userId);
	CustomerAccountEditForm searchEmailCustomer(String email);
	
	
}
