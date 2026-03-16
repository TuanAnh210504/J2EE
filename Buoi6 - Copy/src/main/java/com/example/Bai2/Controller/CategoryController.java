package com.example.Bai2.Controller;

import com.example.Bai2.Model.Category;
import com.example.Bai2.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "Category/create";
    }

    @PostMapping("/create")
    public String create(@Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Category/create";
        }
        categoryService.addCategory(category);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@org.springframework.web.bind.annotation.PathVariable int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return "redirect:/products";
        }
        model.addAttribute("category", category);
        return "Category/create";
    }

    @PostMapping("/edit/{id}")
    public String edit(@org.springframework.web.bind.annotation.PathVariable int id, @Valid Category category,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Category/create";
        }
        categoryService.updateCategory(category);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@org.springframework.web.bind.annotation.PathVariable int id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/products";
    }
}
