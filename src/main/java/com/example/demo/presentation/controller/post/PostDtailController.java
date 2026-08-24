package com.example.demo.presentation.controller.post;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.service.SearchNotifications;
import com.example.demo.domain.service.post.SearchPost;
import com.example.demo.presentation.form.NotificationsForm;
import com.example.demo.presentation.form.post.PostDetailForm;

import lombok.RequiredArgsConstructor;;

@Controller
@RequiredArgsConstructor
public class PostDtailController {
	
	private final SearchPost servicePost;
	private final SearchNotifications searchNotifications; //通知ボタン一時実装用

	/*--- 投稿詳細画面表示リクエスト ---*/
	@GetMapping(POST_DETAIL)
	public String postDtailController(
			Model model,
			@RequestParam Integer postId,
			HttpSession session) {
		//単体動作確認用定数
		if(postId == null) postId = 1;
		if(session.getAttribute("userId") == null) {
			session.setAttribute("userType", "customer");
			session.setAttribute("userName", "yu-za-");
			session.setAttribute("userId", 1);
		}
	    //通知ボタン一時実装用
		Integer userId = (Integer)session.getAttribute("userId");
		List<NotificationsForm> notificationFormList = searchNotifications.getNotifications(userId);
		model.addAttribute("notificationFormList", notificationFormList);
		
		PostDetailForm postDetailForm = servicePost.getPostDetail(postId);
		model.addAttribute("postDetailForm",postDetailForm);
		
		return POST_DETAIL_HTML;
	}
}
