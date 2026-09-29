package com.example.demo.domain.service.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.TagService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.infra.repository.UserRepository;
import com.example.demo.presentation.form.portfolio.PortfolioForm;
import com.example.demo.presentation.form.portfolio.PortfolioListForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioService {

	private final PortfolioRepository portfolioRepository;
	private final UserRepository userRepository;
	private final TagService tagService;

	public List<PortfolioEntity> findByUserId(Integer userId) {
		return portfolioRepository.findByUserUserId(userId);
	}

	public PortfolioEntity findById(Integer portfolioId) {
		return portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new IllegalArgumentException(
						"ポートフォリオが見つかりません"));
	}

	public PortfolioForm findPortfolio(
			Integer portfolioId,
			Integer userId) {

		PortfolioEntity entity = findByIdAndUserId(
				portfolioId,
				userId);

		PortfolioForm form = new PortfolioForm();

		form.setPortfolioId(entity.getPortfolioId());
		form.setDescription(entity.getDescription());
		form.setTempImagePath(entity.getImagePath());
		form.setTagIds(
				tagService.getTagIdsByPortfolioId(
						portfolioId));

		return form;
	}

	public PortfolioEntity findByIdAndUserId(
			Integer portfolioId,
			Integer userId) {

		return portfolioRepository.findByPortfolioIdAndUserUserId(
						portfolioId,
						userId)
				.orElseThrow(() -> new IllegalArgumentException(
						"ポートフォリオが見つかりません"));
	}

	/**
	 * ユーザー種別に応じてポートフォリオを検索する
	 */
	public List<PortfolioListForm> searchPortfolioByUserType(
			Integer userId,
			String selectedTag) {

		if (userId == null) {
			return searchAllPortfolios(selectedTag);
		}

		UserEntity user = userRepository.findById(userId)
				.orElseThrow();

		if (user.getUserType() == 2) {
			return searchPortfoliosByArtisan(
					userId,
					selectedTag);
		}

		return searchAllPortfolios(selectedTag);
	}

	/**
	 * 全ポートフォリオを検索する
	 */
	private List<PortfolioListForm> searchAllPortfolios(
			String selectedTag) {

		List<PortfolioEntity> portfolios;

		if (!hasSelectedTag(selectedTag)) {

			portfolios = portfolioRepository.findAll();

		} else {

			portfolios = portfolioRepository.findByAnyTagName(
					List.of(selectedTag));
		}

		return convertToPortfolioListForm(portfolios);
	}

	/**
	 * Artisan本人のポートフォリオを検索する
	 */
	private List<PortfolioListForm> searchPortfoliosByArtisan(
			Integer userId,
			String selectedTag) {

		List<String> artisanTags = tagService.getArtisanTagNames(userId);

		if (artisanTags.isEmpty()) {
			return List.of();
		}

		if (hasSelectedTag(selectedTag)
				&& !artisanTags.contains(selectedTag)) {

			return List.of();
		}

		List<String> tagNames;

		if (hasSelectedTag(selectedTag)) {

			tagNames = List.of(selectedTag);

		} else {

			tagNames = artisanTags;
		}

		List<PortfolioEntity> portfolios = portfolioRepository.findByAnyTagName(
				tagNames);

		return convertToPortfolioListForm(portfolios);
	}

	/**
	 * PortfolioEntityを一覧表示用Formに変換する
	 */
	public List<PortfolioListForm> convertToPortfolioListForm(
			List<PortfolioEntity> portfolios) {

		List<PortfolioListForm> result = new ArrayList<>();

		for (PortfolioEntity portfolio : portfolios) {

			List<String> tags = tagService.getTagNamesByPortfolioId(
					portfolio.getPortfolioId());

			PortfolioListForm form = new PortfolioListForm();

			form.setPortfolioId(
					portfolio.getPortfolioId());

			form.setImagePath(
					portfolio.getImagePath());

			form.setDescription(
					portfolio.getDescription());

			form.setTags(tags);

			result.add(form);
		}
		return result;
	}

	private boolean hasSelectedTag(String selectedTag) {
		return selectedTag != null
				&& !selectedTag.isBlank();
	}

}