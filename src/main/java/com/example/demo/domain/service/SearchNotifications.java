package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.NotificationEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.NotificationsRepository;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.NotificationsForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchNotifications{
	private final UserRepository userRepository;
	private final NotificationsRepository notificationsRepository;
	
	public List<NotificationsForm> getNotifications(Integer userId) {
		//userIdを引数にUserテーブルからUserEntityを取得
		UserEntity userEntity = userRepository.findById(userId).orElseThrow();
		//userEntityを引数にNotificationEntityのListを取得
		List<NotificationEntity> notificationEntities = notificationsRepository.findByUser(userEntity);
		//NotificationEntityのListをNotificationFormのListに変換
		List<NotificationsForm> notificationsFormList =
		        notificationEntities.stream()
		            .map(NotificationsForm::convertFrom)
		            .toList();		
		return notificationsFormList;
	}
}
