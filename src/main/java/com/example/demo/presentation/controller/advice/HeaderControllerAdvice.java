//package com.example.demo.presentation.controller.advice;
//
//import java.util.Collections;
//import java.util.List;
//
//import jakarta.servlet.http.HttpSession;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import com.example.demo.domain.service.SearchNotifications;
//import com.example.demo.presentation.form.NotificationsForm;
//
//import lombok.RequiredArgsConstructor;
//
//@ControllerAdvice
//@RequiredArgsConstructor
//public class HeaderControllerAdvice {
//
//    private final SearchNotifications searchNotifications;
//
//    @ModelAttribute("notificationFormList")
//    public List<NotificationsForm> setNotificationFormList(
//            HttpSession session) {
//
//        Integer userId =
//                (Integer) session.getAttribute("userId");
//
//        // ゲストなど、ログイン情報がない場合
//        if (userId == null) {
//            return Collections.emptyList();
//        }
//
//        // ログイン済みなら通知を取得
//        return searchNotifications.getNotifications(userId);
//    }
//}