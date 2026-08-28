package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.infra.entity.CandidateEntity;
import com.example.demo.infra.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
	void deleteByCandidate(CandidateEntity candidate);

}
