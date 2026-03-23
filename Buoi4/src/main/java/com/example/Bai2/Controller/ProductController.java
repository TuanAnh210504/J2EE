
package com.example.Bai2.Controller;

import com.example.Bai2.Model.Product;
import com.example.Bai2.Service.CategoryService;
import com.example.Bai2.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
=======
>>>>>>> a8470cd (Initial commit)

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "Product/Product";
    }

    @GetMapping("/create")
    public String create(Model model) {
<<<<<<< HEAD
        model.addAttribute("product", new Product());
=======
        Product product = new Product();
        model.addAttribute("product", product);
>>>>>>> a8470cd (Initial commit)
        model.addAttribute("categories", categoryService.getAllCategories());
        return "Product/create";
    }

    @PostMapping("/create")
<<<<<<< HEAD
    public String create(@Valid Product product, BindingResult result, Model model) {
=======
    public String create(@Valid Product product, BindingResult result, Model model,
            @RequestParam("imageFile") org.springframework.web.multipart.MultipartFile imageFile) {
>>>>>>> a8470cd (Initial commit)
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "Product/create";
        }
<<<<<<< HEAD
=======

        // Handle Image Upload
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

        // Ensure category is fetched from DB and set to the product
        if (product.getCategory() != null && product.getCategory().getId() > 0) {
            com.example.Bai2.Model.Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);
        }

>>>>>>> a8470cd (Initial commit)
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
<<<<<<< HEAD
    public String edit(@PathVariable int id, @Valid Product product, BindingResult result, Model model) {
=======
    public String edit(@PathVariable int id, @Valid Product product, BindingResult result, Model model,
            @RequestParam("imageFile") org.springframework.web.multipart.MultipartFile imageFile) {
>>>>>>> a8470cd (Initial commit)
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "Product/create";
        }
<<<<<<< HEAD
=======

        // Fetch original product to keep unchanged fields like Image
        Product existingProduct = productService.getProductById(id);

        // Handle Image Upload
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
            // Keep old image if no new file uploaded
            product.setImage(existingProduct.getImage());
        }

        // Ensure category is fetched from DB and set to the product
        if (product.getCategory() != null && product.getCategory().getId() > 0) {
            com.example.Bai2.Model.Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);
        }

>>>>>>> a8470cd (Initial commit)
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
