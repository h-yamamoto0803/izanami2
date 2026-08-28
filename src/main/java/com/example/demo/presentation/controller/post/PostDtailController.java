package com.example.demo.presentation.controller.post;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.post.SearchPostDetail;
import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.post.PostDetailForm;

import lombok.RequiredArgsConstructor;;

@Controller
@RequiredArgsConstructor
public class PostDtailController {

	private final SearchPostDetail searchPostDetail;

	/*--- 投稿詳細画面表示リクエスト ---*/
	@PermissionCheck
	@GetMapping(POST_DETAIL)
	public String postDtailController(
			Model model,
			@RequestParam Integer postId,
			HttpSession session) {
		LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);

		PostEntity postEntity = searchPostDetail.getPostDetail(postId);

		UserEntity userEntity = null;
		PostDetailForm postDetailForm = searchPostDetail.convertFrom(postEntity);
		if (loginUser != null) {
			userEntity = loginUser.convertToUserEntity(loginUser);
			// いいね・検討フラグ情報を追加
			postDetailForm = searchPostDetail.alreadyFlag(
					postDetailForm,
					userEntity,
					postEntity);

		}
		LoginUserForm loginUserForm = new LoginUserForm();
		model.addAttribute(TransitionTargetPageNameKeyword.LOGIN_FORM, loginUserForm);

		model.addAttribute(POST_DETAIL_FORM, postDetailForm);

		return POST_DETAIL_HTML;
	}
}
