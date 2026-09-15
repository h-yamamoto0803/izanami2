package com.example.demo.infra.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolio_tags")
@Data
@NoArgsConstructor
public class PortfolioTagEntity {

    @EmbeddedId
    private PortfolioTagId id;

    @ManyToOne
    @MapsId("portfolioId")
    @JoinColumn(name = "portfolio_id")
    private PortfolioEntity portfolio;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PortfolioTagId implements Serializable {

        private static final long serialVersionUID = 1L;

        @Column(name = "portfolio_id")
        private Integer portfolioId;

        @Column(name = "tag_id")
        private Integer tagId;
    }
}