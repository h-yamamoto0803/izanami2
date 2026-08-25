package com.example.demo.presentation.controller.post;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.post.SearchPostDetail;
import com.example.demo.presentation.form.post.PostDetailForm;

import lombok.RequiredArgsConstructor;;

@Controller
@RequiredArgsConstructor
public class PostDtailController {
	
	private final SearchPostDetail searchPostDetail;

	/*--- 投稿詳細画面表示リクエスト ---*/
	@GetMapping(POST_DETAIL)
	public String postDtailController(
			Model model,
			@RequestParam Integer postId,
			HttpSession session) {
				
		PostDetailForm postDetailForm = searchPostDetail.getPostDetail(postId);
		model.addAttribute("postDetailForm",postDetailForm);
		
		return POST_DETAIL_HTML;
	}
}
