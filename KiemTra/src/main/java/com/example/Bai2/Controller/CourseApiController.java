package com.example.Bai2.Controller;

import com.example.Bai2.Model.Course;
import com.example.Bai2.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseApiController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/search")
    public List<Course> liveSearch(@RequestParam(defaultValue = "") String keyword) {
        if (keyword.trim().isEmpty()) {
            return List.of();
        }
        Page<Course> result = courseService.searchCoursesByName(keyword, 0, 5);
        return result.getContent();
    }
}
