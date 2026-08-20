package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
