package com.example.demo.domain.service.post;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.example.demo.presentation.form.post.PostDetailForm;

@Service
public class MockSearchPostImpl implements SearchPost {

	@Override
	public PostDetailForm getPostDetail(Integer postId) {
		PostDetailForm form = new PostDetailForm();
		
		if(postId == 1) {
			form.setPostId(postId);
			form.setUserType("Customer");
			form.setUserName("伝統工芸が好き");
			form.setPostTime(Timestamp.valueOf("2026-08-19 12:00:00"));
			form.setPostTitle("玄関に置ける小さな花器が欲しい");
			form.setPostText("玄関に置ける小さな花器を探しています。落ち着いた色味で、長く使えるものが希望です。手入れが簡単で、日常使いしやすいものだとうれしいです。");
			form.setTags(new String[] {"陶芸","花器"});
			form.setFavoriteCount(21);
		}else if(postId == 2) {
			form.setPostId(postId);
			form.setUserType("Artisan");
			form.setUserName("陶芸おじ");
			form.setPostTime(Timestamp.valueOf("2026-11-28 12:00:00"));
			form.setPostTitle("最近隣に来たものですけど");
			form.setPostText("花器作りすぎちゃったんで幾つかもらってくれませんか");
			form.setTags(new String[] {"陶芸","花器","ご近所"});
			form.setFavoriteCount(8);
		}else {
			return null;
		}
		
		return form;
	}

}
