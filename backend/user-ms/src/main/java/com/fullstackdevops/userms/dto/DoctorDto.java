package com.fullstackdevops.userms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Long socialSecurityNumber;
    private Long userId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
    private String specialty;
}
