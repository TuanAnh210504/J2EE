package com.example.Bai2.Service;

import com.example.Bai2.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "Laptop", "laptop.jpg", 1200.0, 1));
        products.add(new Product(2, "Smartphone", "phone.jpg", 800.0, 1));
        products.add(new Product(3, "Novel", "book.jpg", 15.0, 2));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) {
        int newId = products.stream().mapToInt(Product::getId).max().orElse(0) + 1;
        product.setId(newId);
        products.add(product);
    }

    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
    }

    public void deleteProductById(int id) {
        products.removeIf(p -> p.getId() == id);
    }
}
