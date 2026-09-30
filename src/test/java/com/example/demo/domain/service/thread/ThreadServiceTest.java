package com.example.demo.domain.service.thread;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import com.example.demo.infra.entity.ThreadEntity;
import com.example.demo.infra.repository.ThreadRepository;
import com.example.demo.presentation.form.thread.ThreadForm;

@ExtendWith(MockitoExtension.class)
class ThreadServiceTest {

    @Mock
    private ThreadRepository threadRepository;

    @InjectMocks
    private ThreadService threadService;

    @Test
    void コメント登録時_DB例外が発生した場合_例外が送出される() {

        // 正常な入力値
        ThreadForm form = new ThreadForm();
        form.setPostId(1);
        form.setComment("正常なコメント");

        Integer userId = 1;

        // ThreadRepository.save() が呼ばれたらDB例外を発生させる
        when(threadRepository.save(any(ThreadEntity.class)))
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "テスト用DB例外"));

        // DB例外が上位へ送出されることを確認
        assertThrows(
                DataAccessResourceFailureException.class,
                () -> threadService.insertThread(form, userId));
    }
}