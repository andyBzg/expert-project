package com.example.expertprojectbackend.category.service.impl;

import com.example.expertprojectbackend.category.dto.CategoriesDto;
import com.example.expertprojectbackend.category.repository.CategoryRepository;
import com.example.expertprojectbackend.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoriesDto getListOfCategories() {
        return new CategoriesDto(categoryRepository.getCategoryNames());
    }
}
