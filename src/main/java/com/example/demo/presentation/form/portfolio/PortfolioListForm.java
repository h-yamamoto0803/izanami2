package com.example.demo.presentation.form.portfolio;

import java.util.List;

import lombok.Data;

@Data
public class PortfolioListForm {

    private Integer portfolioId;
    private String imagePath;
    private String description;
    private List<String> tags;
}