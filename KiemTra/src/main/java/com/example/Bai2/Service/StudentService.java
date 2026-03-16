package com.example.Bai2.Service;

import com.example.Bai2.Dto.RegisterDto;
import com.example.Bai2.Model.Role;
import com.example.Bai2.Model.Student;
import com.example.Bai2.Repository.RoleRepository;
import com.example.Bai2.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterDto dto) {
        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setEmail(dto.getEmail());

        // Default role STUDENT
        Role role = roleRepository.findByName("STUDENT");
        if (role == null) {
            role = new Role();
            role.setName("STUDENT");
            roleRepository.save(role);
        }
        student.getRoles().add(role);
        studentRepository.save(student);
    }

    public Optional<Student> findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }
}
