package com.example.Bai2.Service;

import com.example.Bai2.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
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
    }
}
