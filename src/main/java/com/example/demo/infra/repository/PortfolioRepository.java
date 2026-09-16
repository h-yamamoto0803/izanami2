package com.example.demo.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.PortfolioEntity;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {

	List<PortfolioEntity> findByUserUserId(Integer userId);
	Optional<PortfolioEntity>findByPortfolioIdAndUserUserId(
	        Integer portfolioId,
	        Integer userId
	);
}
