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

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
public class PortfolioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "portfolio_id")
	private Integer portfolioId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(name = "image_path", nullable = false, length = 500)
	private String imagePath;

	@Column(name = "description",length = 500,columnDefinition = "TEXT")
	private String description;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private Timestamp updatedAt;
}
