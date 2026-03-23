package com.example.Bai2.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Cấu hình phân quyền
                        .requestMatchers("/products/create", "/products/edit/**", "/products/delete/**",
                                "/categories/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/products", "/home", "/cart/**", "/order/**").hasAnyRole("USER", "ADMIN")

                        // Các request còn lại phải quét xác thực
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // Sử dụng trang đăng nhập tùy chỉnh
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
