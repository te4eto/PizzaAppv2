package org.example.models.dto.requstdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserUpdateDTO {
    String fullName;
    String phoneNumber;
    String street;
    String city;
    String postalCode;
    String country;
}
