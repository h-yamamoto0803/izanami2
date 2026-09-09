package com.example.demo.domain.service.artisan;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CandidateResponseDto;
import com.example.demo.infra.entity.CandidateEntity;
import com.example.demo.infra.entity.NotificationEntity;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.CandidateRepository;
import com.example.demo.infra.repository.NotificationRepository;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.presentation.form.artisan.CandidateForm;
import com.example.demo.presentation.form.common.LoginUserForm;

@Service
public class CandidateService {

	CandidateRepository candidateRepository;
	NotificationRepository notificationRepository;
	PostRepository postRepository;


	public CandidateService(CandidateRepository candidateRepository, NotificationRepository notificationRepository,
			PostRepository postRepository) {
		this.candidateRepository = candidateRepository;
		this.notificationRepository = notificationRepository;
		this.postRepository = postRepository;
	}

	/**
	 * コントローラからリクエストとログインユーザの情報を受け取って
	 * candidatesテーブルに一致するものがあるか判定し追加・削除を行う
	 * 追加の場合通知情報を通知テーブルに追加する
	 * 
	 * @param user: sessionから取得したログインユーザーのID情報
	 * @param form: 送信された対象投稿のID情報
	 * @return 表示層で非同期処理を行うためのDTO
	 */
	@Transactional
	public CandidateResponseDto switchCandidate(LoginUserForm user, CandidateForm form) {
		CandidateResponseDto response = new CandidateResponseDto();

		//通知用キーワード
		final String noteCandidated = "あなたの投稿が検討されました。";

		// 引数をEntityに変換
		PostEntity postEntity = form.convertToPostEntity(form);
		UserEntity userEntity = user.convertToUserEntity(user);

		// 一致する要素がcandidatesテーブルにあるか確認し変更を行う
		// 結果をDTOに記録
		Optional<CandidateEntity> candidated = candidateRepository.findByUserAndPost(userEntity, postEntity);
		if (candidated.isPresent()) {

			CandidateEntity candidate = candidated.get();

			// 先に通知を削除
			notificationRepository.deleteByCandidate(candidate);

			candidateRepository.delete(candidated.get());
			response.setCandidated(false);
		} else {
			CandidateEntity candidate = new CandidateEntity();
			NotificationEntity notificationEntity = new NotificationEntity();
			postEntity = postRepository.findById(postEntity.getPostId()).orElseThrow();
			candidate.setUser(userEntity);
			candidate.setPost(postEntity);
			candidateRepository.save(candidate);

			//通知テーブルに検討されたという情報を追加
			//InsertNotificationメソッドを新規に作成する可能性あり
			notificationEntity.setCandidate(candidate);
			postEntity = postRepository.findById(postEntity.getPostId())
					.orElseThrow();
			notificationEntity.setUser(postEntity.getUser());
			notificationEntity.setMessage(noteCandidated);
			notificationRepository.save(notificationEntity);

			response.setCandidated(true);

		}

		response.setCandidateCount(candidateRepository.countByPost(postEntity));
		response.setPostId(form.getPostId());
		return response;
	}

}
