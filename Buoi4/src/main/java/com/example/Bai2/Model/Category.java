package com.example.Bai2.Model;

<<<<<<< HEAD

=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
>>>>>>> a8470cd (Initial commit)
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
public class Category {
=======
@Entity
@Table(name = "loai_san_pham")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> a8470cd (Initial commit)
    private int id;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
}
