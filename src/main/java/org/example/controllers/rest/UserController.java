package org.example.controllers.rest;

import org.example.mappers.UserMapper;
import org.example.models.dto.UserResponseDTO;
import org.example.models.dto.requstdto.UserRegistrationDTO;
import org.example.models.dto.requstdto.UserUpdateDTO;
import org.example.models.entity.UserEntity;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        UserEntity user = userService.getCurrentUser();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateCurrentUser(@RequestBody UserUpdateDTO dto) {
        UserResponseDTO updatedUser = userService.updateCurrentUser(dto);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegistrationDTO dto) {
        UserResponseDTO registeredUser = userService.registerUser(dto);
        return ResponseEntity.ok(registeredUser);
    }
}
