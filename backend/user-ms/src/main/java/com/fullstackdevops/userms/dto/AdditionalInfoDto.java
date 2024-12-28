package com.fullstackdevops.userms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoDto {
    private Long socialSecurityNumber;
    private Long userId;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String address;
    private String gender;
    private String phoneNumber;
    private String email;

    // Doctor specific
    private String specialty;

    // Staff specific
    private String description;


}
