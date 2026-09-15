package com.example.demo.domain.service.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.PostTagEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.ArtisanTagRepository;
import com.example.demo.infra.repository.CandidateRepository;
import com.example.demo.infra.repository.FavoriteRepository;
import com.example.demo.infra.repository.PostRepository;
import com.example.demo.infra.repository.PostTagRepository;
import com.example.demo.infra.repository.TagRepository;
import com.example.demo.infra.repository.ThreadRepository;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.post.PostListForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostTagRepository postTagRepository;

    private final TagRepository tagRepository;

    private final FavoriteRepository favoriteRepository;

    private final ArtisanTagRepository artisanTagRepository;

    private final UserRepository userRepository;

    private final ThreadRepository threadRepository;

    private final CandidateRepository candidateRepository;

    /**
     * ユーザー種別に応じて投稿を検索します。
     *
     * @param userId ログインユーザーのID
     * @param selectedTag 画面で選択されたタグ
     * @return メニュー画面に表示する投稿一覧
     */
    public List<PostListForm> searchPostByUserType(
            Integer userId,
            String selectedTag) {

        List<PostListForm> postListForm;

        // userIdがない場合はGuestとして全投稿を検索
        if (userId == null) {
            return searchAllPosts(selectedTag);
        }

        // DBにセッション情報と一致するユーザーが存在しない状況は正常ではないので
        // nullではなくthrowとする
        UserEntity user =
                userRepository.findById(userId).orElseThrow();

        if (user.getUserType() == 2) {

            // Artisanの場合は専門タグに関連する投稿を検索
            postListForm =
                    searchPostsByArtisanTags(
                            userId,
                            selectedTag);

        } else {

            // Customerなど、それ以外の場合は全投稿を検索
            postListForm =
                    searchAllPosts(selectedTag);
        }

        return flaggedCheck(
                postListForm,
                user);
    }

    /**
     * いいね・検討フラグを設定します。
     *
     * @param postListForm 投稿一覧Form
     * @param user ログインユーザー
     * @return フラグ適用後の投稿Form
     */
    private List<PostListForm> flaggedCheck(
            List<PostListForm> postListForm,
            UserEntity user) {

        List<Integer> favoritePostIdList =
                favoriteRepository.findPostIdsByUser(user);

        List<Integer> candidatePostIdList =
                candidateRepository.findPostIdsByUser(user);

        for (PostListForm post : postListForm) {

            // いいねフラグ判定
            post.setFavorited(
                    favoritePostIdList.contains(
                            post.getPostId()));

            // 検討フラグ判定
            post.setCandidated(
                    candidatePostIdList.contains(
                            post.getPostId()));
        }

        return postListForm;
    }

    /**
     * 全投稿を検索します。
     *
     * @param selectedTag 画面で選択されたタグ
     * @return 投稿一覧
     */
    private List<PostListForm> searchAllPosts(
            String selectedTag) {

        List<PostEntity> posts;

        // タグが選択されていない場合
        if (selectedTag == null
                || selectedTag.isEmpty()) {

            // 削除されていない投稿を全件取得
            posts =
                    postRepository.findAllByIsDeleted(
                            (byte) 0);

        } else {

            // 選択されたタグが付いている投稿を取得
            posts =
                    postRepository.findByAnyTagName(
                            List.of(selectedTag));
        }

        // Entityを画面表示用Formに変換
        return convertToPostListForm(posts);
    }

    /**
     * Artisan用の投稿を検索します。
     *
     * @param userId ArtisanのユーザーID
     * @param selectedTag 画面で選択されたタグ
     * @return 投稿一覧
     */
    private List<PostListForm> searchPostsByArtisanTags(
            Integer userId,
            String selectedTag) {

        // Artisanに設定されている専門タグを取得
        List<TagEntity> tags =
                artisanTagRepository.findTagsByUserId(
                        userId);

        // 専門タグがない場合は投稿を表示しない
        if (tags.isEmpty()) {
            return List.of();
        }

        // 検索に使用するタグ名
        List<String> tagNames;

        if (selectedTag == null
                || selectedTag.isEmpty()) {

            // タグ未選択の場合は、
            // Artisanの専門タグをすべて使用
            tagNames =
                    tags.stream()
                            .map(TagEntity::getTagName)
                            .toList();

        } else {

            // 選択されたタグがArtisanの専門タグに
            // 含まれているか確認
            boolean isSpecialtyTag = false;

            for (TagEntity tag : tags) {

                if (tag.getTagName()
                        .equals(selectedTag)) {

                    isSpecialtyTag = true;
                    break;
                }
            }

            // 専門タグに含まれていない場合は
            // 投稿を表示しない
            if (!isSpecialtyTag) {
                return List.of();
            }

            // 選択されたタグを検索に使用
            tagNames =
                    List.of(selectedTag);
        }

        // 指定されたタグが付いている投稿を取得
        List<PostEntity> posts =
                postRepository.findByAnyTagName(
                        tagNames);

        // Entityを画面表示用Formに変換
        return convertToPostListForm(posts);
    }

    /**
     * 投稿Entityを画面表示用のPostListFormに変換します。
     *
     * 投稿に紐づいているタグ、
     * いいね数、コメント件数も取得します。
     *
     * @param posts 投稿Entityの一覧
     * @return PostListFormの一覧
     */
    public List<PostListForm> convertToPostListForm(
            List<PostEntity> posts) {

        List<PostListForm> result =
                new ArrayList<>();

        // 投稿を1件ずつ処理
        for (PostEntity post : posts) {

            // 投稿に紐づいているタグ名を格納するList
            List<String> tags =
                    new ArrayList<>();

            // post_tagsから、
            // この投稿に紐づいているタグを取得
            List<PostTagEntity> postTags =
                    postTagRepository.findByIdPostId(
                            post.getPostId());

            // 投稿に紐づいているタグを1件ずつ処理
            for (PostTagEntity postTag : postTags) {

                // tag_idを使用してタグを取得
                TagEntity tag =
                        tagRepository
                                .findById(
                                        postTag.getId()
                                                .getTagId())
                                .orElse(null);

                // タグが存在する場合は
                // タグ名をListに追加
                if (tag != null) {
                    tags.add(tag.getTagName());
                }
            }

            /*
             * 削除されていないコメント件数だけ取得
             *
             * is_deleted
             * 0：未削除
             * 1：削除済み
             */
            long commentCount =
                    threadRepository
                            .countByPostPostIdAndIsDeleted(
                                    post.getPostId(),
                                    (byte) 0);

            // PostEntityをPostListFormに変換
            PostListForm form =
                    new PostListForm(
                            post.getPostId(),
                            post.getUser()
                                    .getUserType()
                                    .toString(),
                            post.getUser()
                                    .getUserName(),
                            post.getCreatedAt()
                                    .toString(),
                            post.getPostTitle(),
                            post.getPostText(),
                            tags,
                            favoriteRepository
                                    .countByPost(post),
                            false,
                            false,
                            commentCount);

            // 変換したFormを結果に追加
            result.add(form);
        }

        // 変換したForm一覧を返す
        return result;
    }

    /**
     * ユーザーIDから投稿を取得します。
     *
     * @param userId ユーザーID
     * @return 投稿一覧
     */
    public List<PostEntity> findByUserId(
            Integer userId) {

        return postRepository
                .findByUserUserId(userId);
    }

    /**
     * ユーザーIDと削除フラグから投稿を取得します。
     *
     * @param userId ユーザーID
     * @param isDeleted 削除フラグ
     * @return 投稿一覧
     */
    public List<PostEntity> findByUserIdAndIsDeleted(
            Integer userId,
            byte isDeleted) {

        return postRepository
                .findByUserUserIdAndIsDeleted(
                        userId,
                        isDeleted);
    }
}