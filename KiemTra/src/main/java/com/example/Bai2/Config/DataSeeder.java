package com.example.Bai2.Config;

import com.example.Bai2.Model.Student;
import com.example.Bai2.Model.Role;
import com.example.Bai2.Repository.StudentRepository;
import com.example.Bai2.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Seed Roles
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = new Role("ADMIN");
                roleRepository.save(adminRole);
            }

            Role studentRole = roleRepository.findByName("STUDENT");
            if (studentRole == null) {
                studentRole = new Role("STUDENT");
                roleRepository.save(studentRole);
            }

            // Seed Admin Account
            if (studentRepository.findByUsername("admin").isEmpty()) {
                Student admin = new Student();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("123"));
                admin.getRoles().add(adminRole);
                studentRepository.save(admin);
            }

            // Seed Student Account
            if (studentRepository.findByUsername("student").isEmpty()) {
                Student student = new Student();
                student.setUsername("student");
                student.setEmail("student@example.com");
                student.setPassword(passwordEncoder.encode("123"));
                student.getRoles().add(studentRole);
                studentRepository.save(student);
            }
        };
    }
}
