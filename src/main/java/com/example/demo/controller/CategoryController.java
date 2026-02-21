package com.example.demo.controller;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Category;
import com.example.demo.entity.CategoryType;
import com.example.demo.service.CategoryService;



@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ================= CREATE =================
    // ADMIN only
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    // ================= READ ALL =================
    // ADMIN & USER
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // ================= READ BY TYPE =================
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/type/{type}")
    public List<Category> getByType(@PathVariable CategoryType type) {
        return categoryService.getCategoriesByType(type);
    }

    // ================= READ BY ID =================
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    // ================= UPDATE =================
    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id,
                                   @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    // ================= DELETE =================
    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "Category deleted successfully";
    }
}
