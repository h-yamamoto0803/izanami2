package com.example.demo.infra.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * post_tagsテーブルのデータをJavaオブジェクトとして扱うためのEntityです。
 *
 * post_tagsは「post_id」と「tag_id」の複合主キーを持つため、
 * 複合キー用のクラスを使用します。
 */
@Entity
@Table(name = "post_tags")
@Data
@NoArgsConstructor
public class PostTagEntity {

    /** 複合主キー */
    @EmbeddedId
    private PostTagId id;

    /**
     * post_tagsの複合主キーを表すクラスです。
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostTagId implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 投稿ID */
        @Column(name = "post_id")
        private Integer postId;

        /** タグID */
        @Column(name = "tag_id")
        private Integer tagId;
    }
}
