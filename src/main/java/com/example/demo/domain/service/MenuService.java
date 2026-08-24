package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.presentation.form.post.PostListForm;

@Service
public class MenuService {

    /**
     * メイン画面に表示する投稿一覧を取得します。
     *
     * 現在はDB連携前のため、仮データを返します。
     *
     * @return 投稿一覧
     */
    public List<PostListForm> getPostList() {

        return List.of(

            new PostListForm(
                "customer",
                "伝統工芸が好き",
                "2026/08/19 12:00",
                "玄関に置ける小さな花器が欲しい",
                "玄関に置ける小さな花器を探しています。落ち着いた色味で、長く使えるものが希望です。",
                "陶芸",
                "花器",
                21
            ),

            new PostListForm(
                "artisan",
                "山田工房",
                "2026/08/18 09:30",
                "暮らしに馴染む一点ものの器",
                "毎日の食卓で使いやすい一点ものの器を制作しています。",
                "陶芸",
                "器",
                12
            ),

            new PostListForm(
                "customer",
                "工芸のある暮らし",
                "2026/08/17 20:10",
                "名刺が入る薄い漆塗りケースがほしい",
                "仕事でも使える、派手すぎない漆塗りの名刺入れがあるとうれしいです。",
                "漆器",
                "小物",
                18
            )
        );
    }
    public List<PostListForm> getPostListByGenre(String genre) {

        List<PostListForm> posts = getPostList();

        // ジャンルが指定されていない場合は全件返す
        if (genre == null || genre.isBlank()) {
            return posts;
        }

        // 指定されたジャンルの投稿だけを返す
        return posts.stream()
                .filter(post -> genre.equals(post.getGenre()))
                .toList();
    }
}