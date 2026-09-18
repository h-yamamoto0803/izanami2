package com.example.demo.presentation.controller.portfolio;

import static com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword.*;
import static com.example.demo.presentation.controller.pageproperty.SessionKeyword.*;
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
import com.example.demo.domain.service.portfolio.EditPortfolioService;
import com.example.demo.infra.entity.TagEntity;
import com.example.demo.presentation.form.common.LoginUserForm;
import com.example.demo.presentation.form.portfolio.PortfolioForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EditPortfolioConfirmController {

    private final ImageService imageService;
    private final HttpSession httpSession;
    private final EditPortfolioService editPortfolioService;
    private final TagService tagService;

    @ModelAttribute(TAGS)
    public List<TagEntity> setTags() {
        return tagService.getAllTagEntities();
    }

    @PermissionCheck
    @PostMapping(EDIT_PORTFOLIO_CONFIRM)
    public String portfolioEditConfirm(
            @Valid @ModelAttribute PortfolioForm form,
            BindingResult bindingResult,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return EDIT_PORTFOLIO_HTML;
        }

        MultipartFile image = form.getImage();

        if (image != null && !image.isEmpty()) {

            try {
                String tempImagePath =
                        imageService.saveTempImage(
                                image,
                                "portfolio"
                        );

                form.setTempImagePath(tempImagePath);

            } catch (IllegalArgumentException e) {

                bindingResult.rejectValue(
                        "image",
                        "image.error",
                        e.getMessage()
                );

                return EDIT_PORTFOLIO_HTML;
            }
        }

        model.addAttribute(
                "tagNames",
                tagService.getTagNamesByIds(
                        form.getTagIds()
                )
        );

        return EDIT_PORTFOLIO_CONFIRM_HTML;
    }
    @PermissionCheck
    @PostMapping(DO_EDIT_PORTFOLIO)
    public String portfolioUpdate(
            @ModelAttribute PortfolioForm form,
            RedirectAttributes redirectAttributes)
            throws IOException {

        LoginUserForm loginUser =
                (LoginUserForm) httpSession.getAttribute(
                        LOGIN_USER);

        Integer userId = loginUser.getUserId();

        editPortfolioService.editPortfolio(
                form,
                userId
        );

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "ポートフォリオを更新しました。"
        );

        return REDIRECT_ACCOUNT;
    }
}