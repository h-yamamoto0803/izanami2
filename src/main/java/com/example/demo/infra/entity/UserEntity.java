package com.example.demo.infra.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * usersテーブルのデータをJavaオブジェクトとして扱うためのEntityです。
 *
 * Entity：
 * DBの1レコードをJavaのオブジェクトとして表現する役割を持ちます。
 *
 * 今回はusersテーブルを対象とするため、
 * UserEntityとusersテーブルを対応付けます。
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * ユーザーID
     *
     * DB上では「user_id」が主キーであり、
     * AUTO INCREMENTによって登録時にDB側で自動採番されます。
     *
     * @Id
     * → この項目が主キーであることをJPAに伝えます。
     *
     * @GeneratedValue
     * → IDの値をDB側のAUTO INCREMENTに任せることを表します。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * ユーザータイプ
     *
     * DB設計では、
     * 1：消費者（Customer）
     * 2：職人（Artisan）
     *
     * と定義されています。
     *
     * ログイン時に、
     * 「Customerとしてログインしようとしているのか」
     * 「Artisanとしてログインしようとしているのか」
     * を判定するために使用します。
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * ユーザー名
     *
     * DBのuser_nameに対応します。
     * VARCHAR(50)で定義されています。
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * メールアドレス
     *
     * DBのemailに対応します。
     *
     * emailにはUNIQUE制約があるため、
     * 同じメールアドレスを複数のユーザーが登録することはできません。
     *
     * ログイン時には、このメールアドレスを検索条件として
     * ユーザーを取得します。
     */
    @Column(name = "email")
    private String email;

    /**
     * パスワード
     *
     * DBのpasswordに対応します。
     *
     * ログイン時には、
     * 入力されたパスワードとDBに保存されているパスワードを
     * 照合するために使用します。
     */
    @Column(name = "password")
    private String password;

    /**
     * 作成日時
     *
     * DBのcreated_atに対応します。
     * DATETIME型なので、JavaではLocalDateTimeで扱います。
     *
     * DB側にDEFAULT CURRENT_TIMESTAMPが設定されているため、
     * 新規登録時にはDBが日時を設定します。
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新日時
     *
     * DBのupdated_atに対応します。
     * DATETIME型なので、JavaではLocalDateTimeで扱います。
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 削除フラグ
     *
     * DB設計では、
     * 0：未削除
     * 1：削除済み
     *
     * と定義されています。
     *
     * ログイン処理では、削除済みユーザーがログインできないように
     * この値を確認する必要があります。
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
}