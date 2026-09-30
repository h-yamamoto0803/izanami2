package com.example.demo.domain.service.portfolio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.service.common.ImageService;
import com.example.demo.infra.entity.PortfolioEntity;
import com.example.demo.infra.repository.PortfolioRepository;
import com.example.demo.infra.repository.PortfolioTagRepository;

@ExtendWith(MockitoExtension.class)
class DeletePortfolioServiceTest {

    @Mock
    private PortfolioService portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private PortfolioTagRepository portfolioTagRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private DeletePortfolioService deletePortfolioService;

    private PortfolioEntity portfolio;

    private final Integer portfolioId = 1;
    private final Integer userId = 1;

    private final String imagePath =
            "/images/portfolio/test.png";

    @BeforeEach
    void setUp() {

        portfolio = new PortfolioEntity();

        portfolio.setPortfolioId(portfolioId);
        portfolio.setImagePath(imagePath);
    }

    /**
     * portfolio_tags削除時にRuntimeExceptionが
     * 発生する場合
     */
    @Test
    void portfolioTags削除失敗時はportfolio本体と画像を削除しない()
            throws IOException {

        when(
            portfolioService.findByIdAndUserId(
                portfolioId,
                userId
            )
        ).thenReturn(portfolio);

        // portfolio_tags削除時に障害発生
        doThrow(
            new RuntimeException(
                "portfolio_tags削除失敗"
            )
        )
        .when(portfolioTagRepository)
        .deleteByIdPortfolioId(portfolioId);

        assertThrows(
            RuntimeException.class,
            () -> deletePortfolioService.deletePortfolio(
                portfolioId,
                userId
            )
        );

        // タグ削除は実行される
        verify(portfolioTagRepository)
            .deleteByIdPortfolioId(portfolioId);

        // タグ削除で失敗するため、
        // portfolio本体の削除には進まない
        verify(
            portfolioRepository,
            never()
        ).delete(any());

        // 画像削除にも進まない
        verify(
            imageService,
            never()
        ).deleteImage(anyString());
    }

    /**
     * portfolio_tagsとportfolio本体削除後、
     * ImageService.deleteImageでIOExceptionが
     * 発生する場合
     */
    @Test
    void DB削除後に画像削除が失敗しても処理を継続する()
            throws IOException {

        when(
            portfolioService.findByIdAndUserId(
                portfolioId,
                userId
            )
        ).thenReturn(portfolio);

        doThrow(
            new IOException("画像削除失敗")
        )
        .when(imageService)
        .deleteImage(imagePath);

        String result =
            deletePortfolioService.deletePortfolio(
                portfolioId,
                userId
            );

        assertEquals(
            "ポートフォリオを削除しました。",
            result
        );

        verify(portfolioTagRepository)
            .deleteByIdPortfolioId(portfolioId);

        verify(portfolioRepository)
            .delete(portfolio);

        verify(imageService)
            .deleteImage(imagePath);
    }
}