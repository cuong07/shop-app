package com.project.shopapp.services.category;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.category.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, CategoryDTO category);

    void deleteCategory(Long id);
}
