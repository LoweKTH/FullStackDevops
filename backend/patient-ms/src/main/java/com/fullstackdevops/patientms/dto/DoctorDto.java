package com.fullstackdevops.patientms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Long socialSecurityNumber;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String specialty;
}
