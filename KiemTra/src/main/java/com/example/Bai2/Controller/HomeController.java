package com.example.Bai2.Controller;

import com.example.Bai2.Model.Course;
import com.example.Bai2.Service.CourseService;
import com.example.Bai2.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping({"/", "/home", "/courses"})
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword) {
        int size = 5;
        Page<Course> coursePage;

        if (keyword != null && !keyword.isEmpty()) {
            coursePage = courseService.searchCoursesByName(keyword, page, size);
            model.addAttribute("keyword", keyword);
        } else {
            coursePage = courseService.getAllCourses(page, size);
        }

        model.addAttribute("coursePage", coursePage);

        Set<Long> enrolledCourseIds = Collections.emptySet();
        String username = getUsernameFromSecurityContext();
        if (username != null && !username.equals("anonymousUser")) {
            enrolledCourseIds = enrollmentService.getEnrolledCourseIds(username);
        }
        model.addAttribute("enrolledCourseIds", enrolledCourseIds);

        return "home";
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
            return oAuth2User.getAttribute("email");
        } else {
            return principal.toString();
        }
    }
}
