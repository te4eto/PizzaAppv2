package org.example.security;

import org.example.constants.UserRole;
import org.example.models.entity.AddressEntity;
import org.example.models.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhoneNumber("000-000-0000");
            admin.setFullName("Admin User");
            admin.setUserRole(UserRole.ADMIN);

            AddressEntity address = new AddressEntity();
            address.setStreet("1 Admin St");
            address.setCity("Adminville");
            address.setPostalCode("00000");
            address.setCountry("Adminland");
            admin.setAddress(address);

            userRepository.save(admin);
            System.out.println("Admin user created: admin/admin123");
        }
    }
}