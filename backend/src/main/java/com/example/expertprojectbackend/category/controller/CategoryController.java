package com.example.expertprojectbackend.category.controller;

import com.example.expertprojectbackend.category.dto.CategoriesDto;
import com.example.expertprojectbackend.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoriesDto> getAllCategories() {
        CategoriesDto categories = categoryService.getListOfCategories();
        return ResponseEntity.ok(categories);
    }
}
