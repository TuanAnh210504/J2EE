package com.example.Bai2.Service;

import com.example.Bai2.Model.Product;
import com.example.Bai2.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.data.domain.*;
>>>>>>> a8470cd (Initial commit)
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

<<<<<<< HEAD
=======
    /**
     * Lấy danh sách sản phẩm với tìm kiếm, phân trang, sắp xếp, lọc category.
     * Câu 1, 2, 3, 4
     */
    public Page<Product> getProducts(String keyword, Integer categoryId, int page, int size, String sort) {
        Sort sortObj;
        if ("asc".equalsIgnoreCase(sort)) {
            sortObj = Sort.by(Sort.Direction.ASC, "price");
        } else if ("desc".equalsIgnoreCase(sort)) {
            sortObj = Sort.by(Sort.Direction.DESC, "price");
        } else {
            sortObj = Sort.by(Sort.Direction.ASC, "id");
        }

        Pageable pageable = PageRequest.of(page, size, sortObj);
        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        boolean hasCategory = categoryId != null && categoryId > 0;

        if (hasKeyword && hasCategory) {
            return productRepository.findByNameContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
        } else if (hasKeyword) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else if (hasCategory) {
            return productRepository.findByCategoryId(categoryId, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

>>>>>>> a8470cd (Initial commit)
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
}
