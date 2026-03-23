package com.example.Bai2.Repository;

import com.example.Bai2.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Tìm kiếm theo tên (Câu 1) + phân trang (Câu 2) + sắp xếp (Câu 3)
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // Lọc theo category (Câu 4)
    Page<Product> findByCategoryId(int categoryId, Pageable pageable);

    // Lọc theo category + tìm kiếm kết hợp
    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(String keyword, int categoryId, Pageable pageable);
}
