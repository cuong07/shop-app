package com.project.shopapp.services.category;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id);
    Page<Category> getAllCategories(PageRequest pageRequest);
    Category updateCategory(Long id, CategoryDTO category);

    void deleteCategory(Long id);
}
