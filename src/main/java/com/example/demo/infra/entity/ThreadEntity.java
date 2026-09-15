package com.example.demo.infra.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**

* threadsテーブルのデータをJavaオブジェクトとして扱うためのEntityです。
*
* DBのthreadsテーブル1レコードをJavaオブジェクトとして表現します。
  */
  @Entity
  @Table(name = "threads")
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public class ThreadEntity {

  /** コメントID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "thread_id")
  private Integer threadId;

  /** コメント対象の投稿 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private PostEntity post;

  /** コメント投稿者 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  /** コメント本文 */
  @Column(name = "comment", nullable = false, length = 1000)
  private String comment;

  /** コメント作成日時 */
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  /** コメント更新日時 */
  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  /** 削除フラグ */
  @Column(name = "is_deleted", nullable = false)
  private Byte isDeleted = 0;
  }
