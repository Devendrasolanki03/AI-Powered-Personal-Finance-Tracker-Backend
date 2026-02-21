package com.example.demo.service;



import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Category;
import com.example.demo.entity.CategoryType;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;



@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // ================= CREATE =================
    public Category createCategory(Category category) {

        if (categoryRepo.existsByName(category.getName())) {
            throw new DuplicateResourceException(
                "Category already exists: " + category.getName()
            );
        }

        return categoryRepo.save(category);
    }

    // ================= READ BY ID =================
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));
    }

    // ================= READ ALL =================
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    // ================= READ BY TYPE =================
    public List<Category> getCategoriesByType(CategoryType type) {
        return categoryRepo.findByType(type);
    }

    // ================= UPDATE =================
    public Category updateCategory(Long id, Category updatedCategory) {

        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        category.setType(updatedCategory.getType());

        return categoryRepo.save(category);
    }

    // ================= DELETE =================
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepo.delete(category);
    }
}
