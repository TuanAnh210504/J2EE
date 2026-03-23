package com.example.Bai2.Service;

import com.example.Bai2.Model.Category;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

import java.util.ArrayList;
=======
import com.example.Bai2.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

>>>>>>> a8470cd (Initial commit)
import java.util.List;

@Service
public class CategoryService {
<<<<<<< HEAD
    private List<Category> categories = new ArrayList<>();

    public CategoryService() {
        categories.add(new Category(1, "Electronics"));
        categories.add(new Category(2, "Books"));
        categories.add(new Category(3, "Clothing"));
    }

    public List<Category> getAllCategories() {
        return categories;
    }

    public Category getCategoryById(int id) {
        return categories.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addCategory(Category category) {
        int newId = categories.stream().mapToInt(Category::getId).max().orElse(0) + 1;
        category.setId(newId);
        categories.add(category);
    }

    public void updateCategory(Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == category.getId()) {
                categories.set(i, category);
                return;
            }
        }
    }

    public void deleteCategoryById(int id) {
        categories.removeIf(c -> c.getId() == id);
=======

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
>>>>>>> a8470cd (Initial commit)
    }
}
