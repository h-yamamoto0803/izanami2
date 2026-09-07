package com.example.demo.domain.service.common;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.common.UserAccountEditForm;

@Service

public class DeleteUserAccountService {

    
    UserRepository userRepository;
    
    
    public DeleteUserAccountService(UserRepository userRepository) {
        
        this.userRepository = userRepository;
    }
    

    public UserAccountEditForm searchIdUser(Integer userId) {
        
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
    public void deleteUser(UserAccountEditForm beforecustomerAccountEditForm, UserEntity deleteUser) {
    	userRepository.save(deleteUser);
    }
	
}
