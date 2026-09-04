package com.example.demo.domain.service.customer;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.FavoriteResponseDto;
import com.example.demo.infra.entity.FavoriteEntity;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.FavoriteRepository;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.customer.FavoriteForm;

@Service
@Transactional
public class Favorite {

	FavoriteRepository repository;


	public Favorite(FavoriteRepository repository) {
		this.repository = repository;
	}

	/**
	 * コントローラからリクエストとログインユーザの情報を受け取って
	 * favoritesテーブルに一致するものがあるか判定し追加・削除を行う
	 * 
	 * @param user: sessionから取得したログインユーザーのID情報
	 * @param form: 送信された対象投稿のID情報
	 * @return 表示層で非同期処理を行うためのDTO
	 */
	public FavoriteResponseDto switchFavorite(LoginUserForm user, FavoriteForm form) {
		FavoriteResponseDto response = new FavoriteResponseDto();

		// 引数をEntityに変換
		PostEntity postEntity = form.convertToPostEntity(form);
		UserEntity userEntity = user.convertToUserEntity(user);

		// 一致する要素がfavoritesテーブルにあるか確認し変更を行う
		// 結果をDTOに記録
		Optional<FavoriteEntity> favorited = repository.findByUserAndPost(userEntity, postEntity);
		if (favorited.isPresent()) {
			repository.delete(favorited.get());
			response.setFavorited(false);
		} else {
			FavoriteEntity favorite = new FavoriteEntity();
			favorite.setUser(userEntity);
			favorite.setPost(postEntity);
			repository.save(favorite);
			response.setFavorited(true);
		}

		// 表示変更に必要な情報をDTOに記録
		response.setFavoriteCount(repository.countByPost(postEntity));
		response.setPostId(form.getPostId());
		return response;

	}

}
