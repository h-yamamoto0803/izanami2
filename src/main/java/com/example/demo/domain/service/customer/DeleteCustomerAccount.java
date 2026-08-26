package com.example.demo.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Service

public class DeleteCustomerAccount {

    
    UserRepository userRepository;
    
    @Autowired
    public DeleteCustomerAccount(UserRepository userRepository) {
        
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
    public void deleteCustomer(CustomerAccountEditForm beforecustomerAccountEditForm, UserEntity deleteUser) {
    	
    }
	
}
