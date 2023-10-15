package com.example.expertprojectbackend.category.repository;

import com.example.expertprojectbackend.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT cat.name FROM Category cat WHERE cat.isDeleted = false")
    List<String> getCategoryNames();
}
