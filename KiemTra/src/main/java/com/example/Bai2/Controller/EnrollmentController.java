package com.example.Bai2.Controller;

import com.example.Bai2.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll/{id}")
    public String enroll(@PathVariable Long id) {
        String username = getUsernameFromSecurityContext();
        
        if (username != null) {
            enrollmentService.enroll(username, id);
        }
        return "redirect:/student/my-courses";
    }

    @PostMapping("/unenroll/{id}")
    public String unenroll(@PathVariable Long id) {
        String username = getUsernameFromSecurityContext();
        
        if (username != null) {
            enrollmentService.unenroll(username, id);
        }
        return "redirect:/student/my-courses";
    }

    @GetMapping("/student/my-courses")
    public String myCourses(Model model) {
        String username = getUsernameFromSecurityContext();
        if (username != null) {
            model.addAttribute("enrollments", enrollmentService.getMyCourses(username));
        }
        return "student/my-courses";
    }

    private String getUsernameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            return ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            return oAuth2User.getAttribute("email"); // Fallback to email for OAuth2
        } else {
            return principal.toString();
        }
    }
}
