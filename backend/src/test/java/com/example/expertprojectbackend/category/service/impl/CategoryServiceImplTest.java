package com.example.expertprojectbackend.category.service.impl;

import com.example.expertprojectbackend.category.dto.CategoriesDto;
import com.example.expertprojectbackend.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getCategories_success() {
        // Given
        List<String> categories = List.of("Math", "Science", "Magic");
        when(categoryRepository.getCategoryNames()).thenReturn(categories);

        // When
        CategoriesDto actual = categoryService.getListOfCategories();

        // Then
        verify(categoryRepository, times(1)).getCategoryNames();
        assertEquals(categories, actual.categories());
        assertFalse(actual.categories().isEmpty());
    }

    @Test
    void getCategories_noCategories_returnsEmptyList() {
        // Given
        List<String> categories = Collections.emptyList();
        when(categoryRepository.getCategoryNames()).thenReturn(categories);

        // When
        CategoriesDto actual = categoryService.getListOfCategories();

        // Then
        verify(categoryRepository, times(1)).getCategoryNames();
        assertEquals(categories, actual.categories());
        assertTrue(actual.categories().isEmpty());
    }
}
