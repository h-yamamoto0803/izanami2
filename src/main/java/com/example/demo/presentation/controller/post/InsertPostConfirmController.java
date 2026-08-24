package com.example.demo.presentation.controller.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;
import com.example.demo.presentation.form.post.InsertPostForm;

@Controller
public class InsertPostConfirmController {
	
	/*
	 * 投稿確認画面表示
	 * @return 投稿確認画面
	 */
	@PostMapping(TransitionTargetPageNameKeyword.INSERT_POST_CONFIRM)
	public String confirmPost(@ModelAttribute InsertPostForm insertPostForm) {
		return TransitionTargetPageNameKeyword.POST_CONFIRM_HTML;
	}
	
//	/*
//	 * 投稿処理
//	 * @return メニュー画面
//	 * 
//	 */
//	@PostMapping(TransitionTargetPageNameKeyword.POST)
//	public String post(@ModelAttribute InsertPostForm insertPostForm) {
//		return TransitionTargetPageNameKeyword.MENU_HTML;
//}
	
}
