package com.example.demo.infra.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * usersテーブルのデータをJavaオブジェクトとして扱うためのEntityです。
 *
 * DBのusersテーブル1レコードをJavaオブジェクトとして表現します。
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	public static final Byte CUSTOMER = 1;
	public static final Byte ARTISAN = 2;

	public static final Byte DELETED = 1;
	public static final Byte NOT_DELETED = 0;
    /** ユーザーID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * ユーザー種別
     *
     * 1：Customer
     * 2：Artisan
     */
    @Column(name = "user_type", nullable = false)
    private Byte userType;

    /** ユーザー名 */
    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    /** メールアドレス */
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /** パスワード */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /** 作成日時 */
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 更新日時 */
    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    /**
     * 削除フラグ
     *
     * 0：未削除
     * 1：削除済み
     */
    @Column(name = "is_deleted", nullable = false)
    private Byte isDeleted = 0;
}