package com.example.Bai2.Model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    @NotBlank(message = "Tên sản phẩm mục không được để trống")
    private String name;
    @Length(min = 0, max = 200, message = "Tên ảnh không quá 200 kí tự")
    private String image;
    @NotNull(message = "Giá không được để trống")
    @Min(value = 1, message = "Giá phải lớn hơn hoặc bằng 1")
    @Max(value = 9999999, message = "Giá không được vượt quá 9.999.999")
    private Double price;

    @NotNull(message = "Danh mục không được để trống")
    private Integer categoryId;
}
