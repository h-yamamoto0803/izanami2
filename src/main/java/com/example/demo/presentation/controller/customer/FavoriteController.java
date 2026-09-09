package com.example.demo.presentation.controller.customer;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.FavoriteService;
import com.example.demo.dto.FavoriteResponseDto;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.customer.FavoriteForm;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class FavoriteController {

	private final FavoriteService favoriteService;

	

	/**
	 * いいねが押された際に呼び出され、処理を行うメソッドを呼び出し結果をjsonで返すコントローラ
	 * PostMapping等関連する範囲の完成以降修正予定
	 * @param form
	 * @param session
	 * @return 対象投稿のID、 追加処理だったか否か、変更後のいいね数を持つDTO
	 */
	@PermissionCheck
	@PostMapping(FAVORITE)
	public FavoriteResponseDto favorite(
			FavoriteForm form,
			HttpSession session){
		LoginUserForm loginUserForm = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
		FavoriteResponseDto response = favoriteService.switchFavorite(loginUserForm, form);
		return response;
	}
}
