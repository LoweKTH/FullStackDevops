package com.fullstackdevops.doctorstaffms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private String socialSecurityNumber;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String specialty;
}
