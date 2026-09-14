package com.example.demo.domain.service.portfolio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.repository.PortfolioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public List<PortfolioEntity> findByUserId(Integer userId) {
        return portfolioRepository.findByUserUserId(userId);
    }
}