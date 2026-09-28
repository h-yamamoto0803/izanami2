package com.example.demo.presentation.form.post;

import java.util.List;

import com.example.demo.presentation.form.thread.ThreadForm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 投稿詳細画面表示用データ
 *
 * 投稿詳細情報とコメント一覧をまとめて保持する。
 */
@Getter
@AllArgsConstructor
public class PostDetailViewData {

    /** 投稿詳細情報 */
    private PostDetailForm postDetailForm;

    /** コメント一覧 */
    private List<ThreadForm> threadList;
}
