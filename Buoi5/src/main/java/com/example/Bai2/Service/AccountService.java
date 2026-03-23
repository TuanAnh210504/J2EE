package com.example.Bai2.Service;

import com.example.Bai2.Dto.RegisterDto;
import com.example.Bai2.Model.Account;
import com.example.Bai2.Model.Role;
import com.example.Bai2.Repository.AccountRepository;
import com.example.Bai2.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account registerNewAccount(RegisterDto registerDto) throws Exception {
        if (accountRepository.findByUsername(registerDto.getUsername()) != null) {
            throw new Exception("Tên đăng nhập đã tồn tại");
        }

        if (accountRepository.findByEmail(registerDto.getEmail()) != null) {
            throw new Exception("Email đã được sử dụng");
        }

        Account account = new Account();
        account.setUsername(registerDto.getUsername());
        account.setEmail(registerDto.getEmail());

        // Băm mật khẩu bằng BCrypt
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Gắn quyền "USER" mặc định cho tài khoản đăng ký mới
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            throw new Exception("Quyền USER chưa được cấu hình trong Database");
        }
        account.setRole(userRole);

        return accountRepository.save(account);
    }
}
