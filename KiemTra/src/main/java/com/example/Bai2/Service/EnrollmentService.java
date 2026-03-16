package com.example.Bai2.Service;

import com.example.Bai2.Model.Course;
import com.example.Bai2.Model.Enrollment;
import com.example.Bai2.Model.Student;
import com.example.Bai2.Repository.CourseRepository;
import com.example.Bai2.Repository.EnrollmentRepository;
import com.example.Bai2.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public boolean enroll(String username, Long courseId) {
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            Student student = studentOpt.get();
            Course course = courseOpt.get();

            if (!enrollmentRepository.existsByStudentAndCourse(student, course)) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollment.setEnrollDate(LocalDate.now());
                enrollmentRepository.save(enrollment);
                return true;
            }
        }
        return false;
    }

    public List<Enrollment> getMyCourses(String username) {
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        if (studentOpt.isPresent()) {
            return enrollmentRepository.findByStudent(studentOpt.get());
        }
        return List.of();
    }

    public java.util.Set<Long> getEnrolledCourseIds(String username) {
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        if (studentOpt.isPresent()) {
            return enrollmentRepository.findByStudent(studentOpt.get())
                    .stream()
                    .map(e -> e.getCourse().getId())
                    .collect(java.util.stream.Collectors.toSet());
        }
        return java.util.Collections.emptySet();
    }
    
    public boolean unenroll(String username, Long courseId) {
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            Student student = studentOpt.get();
            Course course = courseOpt.get();

            if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
                enrollmentRepository.deleteByStudentAndCourse(student, course);
                return true;
            }
        }
        return false;
    }
}
