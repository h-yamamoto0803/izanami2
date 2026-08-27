package com.example.demo.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.infra.entity.ArtisanTagEntity;
import com.example.demo.infra.entity.ArtisanTagEntity.ArtisanTagId;
import com.example.demo.infra.entity.TagEntity;

public interface ArtisanTagRepository extends JpaRepository<ArtisanTagEntity, ArtisanTagId> {

	List<ArtisanTagEntity> findByIdUserId(Integer userId);
	@Query("""
		    SELECT t
		    FROM ArtisanTagEntity a
		    JOIN TagEntity t
		      ON a.id.tagId = t.tagId
		    WHERE a.id.userId = :userId
		""")
		List<TagEntity> findTagsByUserId(@Param("userId") Integer userId);
}
