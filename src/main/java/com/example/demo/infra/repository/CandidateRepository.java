package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Integer> {

}
