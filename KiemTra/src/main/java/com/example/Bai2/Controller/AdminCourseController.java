package com.example.Bai2.Controller;

import com.example.Bai2.Model.Course;
import com.example.Bai2.Service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "admin/courses/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        return "admin/courses/create";
    }

    @PostMapping("/create")
    public String create(@Valid Course course, BindingResult result, Model model, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "admin/courses/create";
        }
        saveImage(course, imageFile);
        courseService.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("course", course);
        return "admin/courses/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @Valid Course course, BindingResult result, Model model, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        if (result.hasErrors()) {
            course.setId(id);
            return "admin/courses/edit";
        }
        Course existingCourse = courseService.findById(id).orElseThrow();
        course.setImage(existingCourse.getImage()); // preserve existing image by default
        
        saveImage(course, imageFile);
        courseService.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa học phần thành công!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/courses";
    }
    
    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.restore(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã khôi phục học phần!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể khôi phục học phần.");
        }
        return "redirect:/admin/courses";
    }
    
    private void saveImage(Course course, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String folder = "src/main/resources/static/images/courses/";
                Path path = Paths.get(folder);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                String originalFilename = imageFile.getOriginalFilename();
                String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
                String newFileName = UUID.randomUUID().toString() + extension;
                
                Path filePath = path.resolve(newFileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                course.setImage("/images/courses/" + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
