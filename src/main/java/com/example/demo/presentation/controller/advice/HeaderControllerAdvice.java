package com.example.demo.presentation.controller.advice;

import java.util.Collections;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.domain.service.SearchNotifications;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.NotificationsForm;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class HeaderControllerAdvice {

    private final SearchNotifications searchNotifications;

    @ModelAttribute("notificationFormList")
    public List<NotificationsForm> setNotificationFormList(
            HttpSession session) {
    	LoginUserForm loginUserForm = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
    	// ゲストなど、ログイン情報がない場合
    	if (loginUserForm == null) {
    		return Collections.emptyList();
    	}
    	
    	// ログイン済みなら通知を取得
        Integer userId = loginUserForm.getUserId();
        return searchNotifications.getNotifications(userId);
    }
}