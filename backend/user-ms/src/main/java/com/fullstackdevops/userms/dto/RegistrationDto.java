package com.fullstackdevops.userms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private UserDto userDetails;
    private AdditionalInfoDto additionalInfoDetails;
}
