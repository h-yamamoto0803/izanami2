package com.example.demo.infra.repository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.infra.entity.PostEntity;
import com.example.demo.infra.entity.UserEntity;

/**
 * usersテーブルに対するDBアクセスを担当するRepositoryです。
 *
 * Repository：
 * DBへの検索・登録・更新・削除などの処理を担当します。
 *
 * 今回はログイン処理で、
 * 「入力されたメールアドレスに該当するユーザーを取得する」
 * ために使用します。
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * メールアドレスを条件にユーザーを検索します。
     *
     * Spring Data JPAでは、メソッド名から検索条件を判断して
     * SQLを自動的に生成してくれます。
     *
     * findByEmail
     * ↓
     * 「emailが一致するデータを検索する」
     *
     * DBのemailにはUNIQUE制約が設定されているため、
     * 1つのメールアドレスに対してユーザーは1人だけです。
     *
     * @param email ログイン画面から入力されたメールアドレス
     * @return メールアドレスに一致するユーザー
     *
     *         ユーザーが存在する場合 → UserEntityが入る
     *         ユーザーが存在しない場合 → empty
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * 削除状態で絞り込んで、投稿を全件取得。投稿日時が新しい順になっている。
     * @param isDeleted
     * @return
     */
    List<PostEntity> findAllByIsDeletedOrderByCreatedAtDesc(Byte isDeleted);
    
    /**
     * @query タグテーブルとタグ_ポストテーブルをJOINし、
     * 			削除されていない＆タグnameが一致するポストをSELECT
     * @param tagNames
     * @return List<PostEntity>
     */
    @Query(value = """
			SELECT DISTINCT p.*
			FROM posts p
			INNER JOIN post_tags pt
				ON pt.post_id = p.post_id
			INNER JOIN tags t
				ON t.tag_id = pt.tag_id
			WHERE p.is_deleted = 0
			  AND t.tag_name IN (:tagNames)
			ORDER BY p.created_at DESC
			""", nativeQuery = true)
    
    /**
     * SELECT DISTINCT -> 重複をまとめる。
     *（タグのJOINによって）同じ投稿が複数行取得されても投稿は1件として取得する
     * INNER JOIN ??? ON ### -> ###で、２つのテーブルをどの条件で結合するか
     * WHERE ??? AND ### -> 条件で絞り込み
     * IN(:???) -> Java側で使いたい値???を代入できる Javaの＠Param("???")が対応している
     */
	List<PostEntity> findByAnyTagName(@Param("tagNames") Collection<String> tagNames);

	
	Optional<UserEntity> findById(Integer userId);
	
	
	
}

