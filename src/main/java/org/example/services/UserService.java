package org.example.services;

import org.example.mappers.UserMapper;
import org.example.models.dto.UserResponseDTO;
import org.example.models.dto.requstdto.UserRegistrationDTO;
import org.example.models.dto.requstdto.UserUpdateDTO;
import org.example.models.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity user = userMapper.toUserEntity(dto);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public UserResponseDTO updateCurrentUser(UserUpdateDTO dto) {
        UserEntity user = getCurrentUser();
        if (user == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        userMapper.updateUserEntity(user, dto);
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserDTO(updatedUser);
    }
}