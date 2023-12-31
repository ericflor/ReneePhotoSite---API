package com.renee.PhotoBlog.security;

import com.renee.PhotoBlog.model.User;
import com.renee.PhotoBlog.model.UserRole;
import com.renee.PhotoBlog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        User adminUser = User.builder()
                .username("ReneeMichael")
                .password(passwordEncoder.encode("rmichael$"))
                .role(UserRole.ADMIN)
                .build();

        if (userRepository.findByUsername(adminUser.getUsername()) == null) {
            userRepository.save(adminUser);
        }
    }
}
