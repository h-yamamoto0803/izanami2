package com.example.demo.domain.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.NotificationEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.NotificationsRepository;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.common.NotificationsForm;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SearchNotificationsService{
	private final UserRepository userRepository;
	private final NotificationsRepository notificationsRepository;
	
	/**
	 * userIdから、UserEntity→NotificationEntityのListを取得し、
	 * NotificationFormのListに変換し返すメソッド。
	 * @param userId
	 * @return List<NotificationsForm>
	 */
	public List<NotificationsForm> getNotifications(Integer userId) {
		
		//userIdを引数にUserテーブルからUserEntityを取得
		UserEntity userEntity = userRepository.findById(userId).orElseThrow();
		
		//userEntityを引数にNotificationEntityのListを取得
		List<NotificationEntity> notificationEntities = notificationsRepository.findByUser(userEntity);
		
		//NotificationEntityのListをNotificationFormのListに変換
		List<NotificationsForm> notificationsFormList =
		        notificationEntities.stream()
			        .filter(entity -> { //投稿のisDeletedが0（＝削除済みでない）なら
			            Byte isDeleted =
			                entity.getCandidate().getPost().getIsDeleted();  //　NotificationEntity→CandidateEntity→PostEntity→getIsDeleted()
			            return isDeleted != null && isDeleted == 0;
			        })	
		        	.map(NotificationsForm::convertFrom) //NotificationFormに変換
		            .toList(); //Listとして格納
		return notificationsFormList;
	}
}
