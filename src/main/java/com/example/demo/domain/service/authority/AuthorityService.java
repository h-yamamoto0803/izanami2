package com.example.demo.domain.service.authority;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.common.TagService;
import com.example.demo.infra.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthorityService {
	
	/** タグ情報を取得するサービス */
	private final TagService tagService;

	/**
	 * ユーザーが指定した投稿にコメントする権限を確認する。
	 *
	 * CUSTOMERの場合は、投稿のタグに関係なくコメント権限あり。
	 * ARTISANの場合は、専門タグと投稿タグが一致すればコメントする権限あり。
	 *
	 * @param user ユーザーEntity
	 * @param postId 投稿ID
	 * @return 権限がある場合true、権限がない場合false
	 */
	public boolean hasCommentAuthority(
			UserEntity user,
			Integer postId) {
		
		// Customerはすべての投稿にコメント可能
		if (UserEntity.CUSTOMER.equals(user.getUserType())) {
			return true;
		}
		
		if (UserEntity.ARTISAN.equals(user.getUserType())) {

			// Artisanの専門タグを取得
			List<String> artisanTags =
					tagService.getArtisanTagNames(
							user.getUserId());

			// 投稿に設定されたタグを取得
			List<String> postTags =
					tagService.getTagNamesByPostId(
							postId);

			// Artisanの専門タグと投稿タグを比較
			for (String artisanTag : artisanTags) {

				if (postTags.contains(artisanTag)) {
					return true;
				}
			}
		}

		return false;
	}
}
