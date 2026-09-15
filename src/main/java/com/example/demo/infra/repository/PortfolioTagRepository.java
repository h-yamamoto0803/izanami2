package com.example.demo.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.PortfolioTagEntity;
import com.example.demo.infra.entity.PortfolioTagEntity.PortfolioTagId;

public interface PortfolioTagRepository extends JpaRepository<PortfolioTagEntity, PortfolioTagId> {

	List<PortfolioTagEntity> findByIdPortfolioId(Integer portfolioId);
	List<PortfolioTagEntity> findByIdTagId(Integer tagId);
	void deleteByIdPortfolioId(Integer portfolioId);
	List<PortfolioTagEntity> findByTagTagName(String tagName);
}