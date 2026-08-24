package com.example.demo.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * メイン画面を表示するControllerです。
 */
@Controller
public class MenuController {

    /**
     * ゲスト用のメイン画面を表示します。
     *
     * @return メイン画面
     */
    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }
}
