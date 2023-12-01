package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id);
    List<Category> getAllCategory();
    Category updateCategory(Long id, CategoryDTO category);

    void deleteCategory(Long id);
}
