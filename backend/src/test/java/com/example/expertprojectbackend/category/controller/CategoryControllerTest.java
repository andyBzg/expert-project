package com.example.expertprojectbackend.category.controller;

import com.example.expertprojectbackend.category.dto.CategoriesDto;
import com.example.expertprojectbackend.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void getAllCategories_success() {
        // Given
        List<String> categories = List.of("Math", "Science", "Magic");
        CategoriesDto categoriesDto = new CategoriesDto(categories);
        when(categoryService.getListOfCategories()).thenReturn(categoriesDto);

        // When
        ResponseEntity<CategoriesDto> actual = categoryController.getAllCategories();

        // Then
        verify(categoryService, times(1)).getListOfCategories();
        assertEquals(categoriesDto, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void getAllCategories_noCategories_returnsEmptyList() {
        // Given
        CategoriesDto categoriesDto = new CategoriesDto(Collections.emptyList());
        when(categoryService.getListOfCategories()).thenReturn(categoriesDto);

        // When
        ResponseEntity<CategoriesDto> actual = categoryController.getAllCategories();

        // Then
        verify(categoryService, times(1)).getListOfCategories();
        assertEquals(categoriesDto, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(actual.getBody().categories().isEmpty());
    }
}
