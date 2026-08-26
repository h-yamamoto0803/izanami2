package com.example.demo.presentation.form.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.demo.infra.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountEditForm {
@NotBlank
private String userName;
@NotBlank
@Email
private String email;
@NotBlank
@Size(min=6)
private String password;
@NotBlank
private String passwordConfirm;

public static UserEntity convertTo(CustomerAccountEditForm customerAccountEditForm) {
     return new UserEntity(
             null,
             null,
             customerAccountEditForm.getUserName(),
             customerAccountEditForm.getEmail(),
             customerAccountEditForm.getPassword(),
             null,
             null,
             null
             
             );
}


}