package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Product;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category saveCategory(Category category);
    Optional<Category> updateCategory(Long id, Category category);
    void deleteCategoryById(Long id);
}
