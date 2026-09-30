package com.example.demo.domain.service.portfolio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.domain.service.common.TagService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.infra.repository.PortfolioTagRepository;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

@ExtendWith(MockitoExtension.class)
class EditPortfolioServiceTest {

    @Mock
    private PortfolioService portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private PortfolioTagRepository portfolioTagRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private EditPortfolioService editPortfolioService;

    private PortfolioForm form;
    private PortfolioEntity entity;

    private final Integer portfolioId = 1;
    private final Integer userId = 1;

    @BeforeEach
    void setUp() {

        form = new PortfolioForm();
        form.setPortfolioId(portfolioId);
        form.setDescription("テスト用作品説明");

        // 画像変更なし
        form.setTempImagePath("/images/portfolio/test.png");

        form.setTagIds(List.of(10));

        entity = new PortfolioEntity();
        entity.setPortfolioId(portfolioId);
        entity.setImagePath("/images/portfolio/test.png");
        entity.setDescription("変更前作品説明");
    }

    /**
     * portfolio本体更新後、
     * portfolio_tags削除時に例外が発生する場合
     */
    @Test
    void portfolioTags削除失敗時に更新処理を中断する()
            throws IOException {

        // 更新対象のポートフォリオを返す
        when(
            portfolioService.findByIdAndUserId(
                portfolioId,
                userId
            )
        ).thenReturn(entity);

        // portfolio_tags削除時に障害を発生させる
        doThrow(
            new RuntimeException(
                "portfolio_tags削除失敗"
            )
        )
        .when(portfolioTagRepository)
        .deleteByIdPortfolioId(portfolioId);

        // 実行すると例外になることを確認
        assertThrows(
            RuntimeException.class,
            () -> editPortfolioService.editPortfolio(
                form,
                userId
            )
        );

        // portfolio本体のsaveまでは呼ばれている
        verify(portfolioRepository)
            .save(entity);

        // portfolio_tags削除が呼ばれている
        verify(portfolioTagRepository)
            .deleteByIdPortfolioId(portfolioId);

        // 削除で失敗しているので、
        // 新しいタグ登録には進まない
        verify(
            portfolioTagRepository,
            never()
        ).saveAll(any());

        // 画像変更なしなので画像削除も行わない
        verify(
            imageService,
            never()
        ).deleteImage(any());
    }

    /**
     * portfolio_tagsの削除後、
     * 新しいportfolio_tags登録時に例外が発生する場合
     */
    @Test
    void portfolioTags登録失敗時に更新処理を中断する()
            throws IOException {

        when(
            portfolioService.findByIdAndUserId(
                portfolioId,
                userId
            )
        ).thenReturn(entity);

        TagEntity tag = new TagEntity();

        // tagId=10のTagEntityを返す
        when(
            tagService.findById(10)
        ).thenReturn(tag);

        // saveAll時に障害を発生させる
        doThrow(
            new RuntimeException(
                "portfolio_tags登録失敗"
            )
        )
        .when(portfolioTagRepository)
        .saveAll(any());

        assertThrows(
            RuntimeException.class,
            () -> editPortfolioService.editPortfolio(
                form,
                userId
            )
        );

        // portfolio本体更新
        verify(portfolioRepository)
            .save(entity);

        // 既存タグ削除
        verify(portfolioTagRepository)
            .deleteByIdPortfolioId(portfolioId);

        // 新しいタグ登録を試行
        verify(portfolioTagRepository)
            .saveAll(any());

        // タグ更新失敗後には画像削除へ進まない
        verify(
            imageService,
            never()
        ).deleteImage(any());
    }
}