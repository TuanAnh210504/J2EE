package com.example.Bai2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên học phần không được để trống")
    @Column(nullable = false)
    private String name;

    private String image;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Min(value = 1, message = "Số tín chỉ phải lớn hơn hoặc bằng 1")
    private Integer credits;

    @NotBlank(message = "Giảng viên không được để trống")
    @NotBlank(message = "Giảng viên không được để trống")
    private String lecturer;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted = false;
}
