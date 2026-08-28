package com.example.demo.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.NotificationEntity;
import com.example.demo.infra.entity.UserEntity;

public interface NotificationsRepository extends JpaRepository<NotificationEntity, Integer> {
	public List<NotificationEntity> findByUser(UserEntity user);
}
