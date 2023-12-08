package com.explorer.equipo3.service;

import com.explorer.equipo3.controller.CategoryController;
import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.repository.ICategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{

    private static final Logger logger = LogManager.getLogger(CategoryController.class);

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> updateCategory(Long id, Category category) {
        logger.info("Busqueda de categoria a actualizar");
        Optional<Category> categoryExist = categoryRepository.findById(id);
        Category categoryOptional = null;
        if (categoryExist.isPresent()){
            logger.info("la categoria fue encontrada");
            Category catDB = categoryExist.orElseThrow();
            catDB.setName(category.getName());
            categoryOptional = categoryRepository.save(catDB);
        }
        return Optional.ofNullable(categoryOptional);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
