package org.example.mappers;

import org.example.constants.UserRole;
import org.example.models.dto.AddressDTO;
import org.example.models.dto.UserResponseDTO;
import org.example.models.dto.requstdto.UserRegistrationDTO;
import org.example.models.dto.requstdto.UserUpdateDTO;
import org.example.models.entity.AddressEntity;
import org.example.models.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity toUserEntity(UserRegistrationDTO dto) {
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setFullName(dto.getFullName());
        user.setUserRole(UserRole.CUSTOMER);

        AddressEntity address = new AddressEntity();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        user.setAddress(address);

        return user;
    }

    public void updateUserEntity(UserEntity user, UserUpdateDTO dto) {
        if (dto.getFullName() != null && !dto.getFullName().trim().isEmpty()) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().trim().isEmpty()) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        AddressEntity address = user.getAddress();
        if (address == null) {
            address = new AddressEntity();
            user.setAddress(address);
        }
        if (dto.getStreet() != null && !dto.getStreet().trim().isEmpty()) {
            address.setStreet(dto.getStreet());
        }
        if (dto.getCity() != null && !dto.getCity().trim().isEmpty()) {
            address.setCity(dto.getCity());
        }
        if (dto.getPostalCode() != null && !dto.getPostalCode().trim().isEmpty()) {
            address.setPostalCode(dto.getPostalCode());
        }
        if (dto.getCountry() != null && !dto.getCountry().trim().isEmpty()) {
            address.setCountry(dto.getCountry());
        }
    }

    public UserResponseDTO toUserDTO(UserEntity user) {
        AddressEntity address = user.getAddress();
        AddressDTO addressDTO = address != null
                ? new AddressDTO(address.getStreet(), address.getCity(), address.getPostalCode(), address.getCountry())
                : null;

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getPhoneNumber(),
                addressDTO
        );
    }
}