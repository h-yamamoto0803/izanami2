package com.example.demo.presentation.controller.portfolio;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.common.ImageService;
import com.example.demo.domain.service.common.TagService;
import com.example.demo.domain.service.portfolio.InsertPortfolioService;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InsertPortfolioConfirmController {

    private final InsertPortfolioService insertPortfolioService;
    private final ImageService imageService;
    private final HttpSession httpSession;
    private final TagService tagService;
    
    @ModelAttribute(TAGS)
    public List<TagEntity> setTags() {
        return tagService.getAllTagEntities();
    }
    
    @PermissionCheck
    @PostMapping(INSERT_PORTFOLIO_CONFIRM)
    public String portfolioConfirm(
            @Valid @ModelAttribute PortfolioForm form,
            BindingResult bindingResult,Model model) throws IOException {
    	
        if (bindingResult.hasErrors()) {
            return INSERT_PORTFOLIO_HTML;
        }

        MultipartFile image = form.getImage();

        if (image == null || image.isEmpty()) {
            bindingResult.rejectValue(
                    "image",
                    "image.empty",
                    "作品画像を選択してください"
            );
            return INSERT_PORTFOLIO_HTML;
        }

        try {
            form.setTempImagePath(
                    imageService.saveTempImage(
                            image,
                            "portfolio"
                    )
            );
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue(
                    "image",
                    "image.error",
                    e.getMessage()
            );
           
            
            return INSERT_PORTFOLIO_HTML;
        }
        model.addAttribute(
                "tagNames",
                tagService.getTagNamesByIds(form.getTagIds()));
        return INSERT_PORTFOLIO_CONFIRM_HTML;
    }

    @PermissionCheck
    @PostMapping(DO_INSERT_PORTFOLIO)
    public String portfolioInsert(
            @ModelAttribute PortfolioForm form,
            RedirectAttributes redirectAttributes) throws IOException {

        LoginUserForm loginUser =
                (LoginUserForm) httpSession.getAttribute("loginUser");

        Integer userId = loginUser.getUserId();

        insertPortfolioService.insertPortfolio(
                form,
                userId
        );

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "ポートフォリオを登録しました。"
        );

        return REDIRECT_MENU;
    }

}