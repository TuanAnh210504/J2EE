package com.example.Bai2.Service;

import com.example.Bai2.Model.Course;
import com.example.Bai2.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private com.example.Bai2.Repository.EnrollmentRepository enrollmentRepository;

    public Page<Course> getAllCourses(int page, int size) {
        return courseRepository.findByIsDeletedFalse(PageRequest.of(page, size));
    }

    public Page<Course> searchCoursesByName(String keyword, int page, int size) {
        return courseRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(keyword, PageRequest.of(page, size));
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + id));
        
        // Throw exception if course has active enrollments
        if (enrollmentRepository.existsByCourse(course)) {
            throw new IllegalStateException("Không thể xóa học phần. Hiện đang có sinh viên đăng ký học phần này.");
        }
        
        course.setDeleted(true);
        courseRepository.save(course);
    }
    
    @Transactional
    public void restore(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course id: " + id));
        course.setDeleted(false);
        courseRepository.save(course);
    }
}
