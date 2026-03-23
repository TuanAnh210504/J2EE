package com.example.Bai2.Config;

import com.example.Bai2.Model.Account;
import com.example.Bai2.Model.Role;
import com.example.Bai2.Repository.AccountRepository;
import com.example.Bai2.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataSeeder {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

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

            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = new Role("USER");
                roleRepository.save(userRole);
            }

            // Seed Admin Account
            if (accountRepository.findByUsername("admin") == null) {
                Account admin = new Account();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("123"));
                admin.setRole(adminRole);
                accountRepository.save(admin);
            }

            // Seed User Account
            if (accountRepository.findByUsername("user") == null) {
                Account user = new Account();
                user.setUsername("user");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRole(userRole);
                accountRepository.save(user);
            }
        };
    }
}
