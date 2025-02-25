package com.fullstackdevops.doctorstaffms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long socialSecurityNumber;
    private Long userId;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String address;
    private String gender;
    private String email;
    private String phoneNumber;
}
