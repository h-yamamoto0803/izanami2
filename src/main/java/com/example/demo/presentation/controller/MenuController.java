package com.example.demo.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.presentation.form.post.PostListForm;

@Controller
public class MenuController {

    @GetMapping("/menu")
    public String showMenu(Model model) {

        List<PostListForm> posts = List.of(

            new PostListForm(
                "customer",
                "伝統工芸が好き",
                "2026/08/19 12:00",
                "玄関に置ける小さな花器が欲しい",
                "玄関に置ける小さな花器を探しています。落ち着いた色味で、長く使えるものが希望です。",
                "陶芸",
                "花器",
                21
            ),

            new PostListForm(
                "artisan",
                "山田工房",
                "2026/08/18 09:30",
                "暮らしに馴染む一点ものの器",
                "毎日の食卓で使いやすい一点ものの器を制作しています。",
                "陶芸",
                "器",
                12
            ),

            new PostListForm(
                "customer",
                "工芸のある暮らし",
                "2026/08/17 20:10",
                "名刺が入る薄い漆塗りケースがほしい",
                "仕事でも使える、派手すぎない漆塗りの名刺入れがあるとうれしいです。",
                "漆器",
                "小物",
                18
            )
        );

        model.addAttribute("posts", posts);
        model.addAttribute("userType", "guest");

        return "menu";
    }
}