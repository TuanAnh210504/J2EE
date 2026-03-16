package com.example.Bai2.Controller;

import com.example.Bai2.Dto.RegisterDto;
import com.example.Bai2.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public String login() {
        return "student/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "student/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDto") RegisterDto registerDto,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "student/register";
        }
        
        if (studentService.findByUsername(registerDto.getUsername()).isPresent()) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "student/register";
        }

        studentService.register(registerDto);
        return "redirect:/login?success";
    }
}
