package com.example.demo.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.infra.entity.PortfolioEntity;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
	@Query("""
	        SELECT DISTINCT p
	        FROM PortfolioEntity p
	        JOIN PortfolioTagEntity pt
	            ON pt.portfolio.portfolioId = p.portfolioId
	        JOIN TagEntity t
	            ON pt.tag.tagId = t.tagId
	        WHERE t.tagName IN :tagNames
	        """)
	List<PortfolioEntity> findByAnyTagName(
	        @Param("tagNames") List<String> tagNames);
	List<PortfolioEntity> findByUserUserId(Integer userId);
	Optional<PortfolioEntity>findByPortfolioIdAndUserUserId(
	        Integer portfolioId,
	        Integer userId
	);
}
