
package com.example.Bai2.Controller;

import com.example.Bai2.Model.Product;
import com.example.Bai2.Service.CategoryService;
import com.example.Bai2.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Câu 1, 2, 3, 4 – Search + Pagination + Sort + Category filter
    @GetMapping("")
    public String index(Model model,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String sort) {

        Page<Product> productPage = productService.getProducts(keyword, categoryId, page, size, sort);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId != null ? categoryId : 0);
        model.addAttribute("sort", sort);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Product/Product";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Product/create";
    }

    @PostMapping("/create")
    public String create(@Valid Product product, BindingResult result, Model model,
            @RequestParam("imageFile") org.springframework.web.multipart.MultipartFile imageFile) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "Product/create";
        }

        if (!imageFile.isEmpty()) {
            try {
                String fileName = java.util.UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                java.nio.file.Path path = java.nio.file.Paths.get("src/main/resources/static/images/" + fileName);
                java.nio.file.Files.createDirectories(path.getParent());
                java.nio.file.Files.copy(imageFile.getInputStream(), path,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                product.setImage("/images/" + fileName);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        if (product.getCategory() != null && product.getCategory().getId() > 0) {
            com.example.Bai2.Model.Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);
        }

        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Product/create";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @Valid Product product, BindingResult result, Model model,
            @RequestParam("imageFile") org.springframework.web.multipart.MultipartFile imageFile) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "Product/create";
        }

        Product existingProduct = productService.getProductById(id);

        if (!imageFile.isEmpty()) {
            try {
                String fileName = java.util.UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                java.nio.file.Path path = java.nio.file.Paths.get("src/main/resources/static/images/" + fileName);
                java.nio.file.Files.createDirectories(path.getParent());
                java.nio.file.Files.copy(imageFile.getInputStream(), path,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                product.setImage("/images/" + fileName);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            product.setImage(existingProduct.getImage());
        }

        if (product.getCategory() != null && product.getCategory().getId() > 0) {
            com.example.Bai2.Model.Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);
        }

        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
