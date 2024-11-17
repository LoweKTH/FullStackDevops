package com.fullstackdevops.doctorstaffms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private Long socialSecurityNumber;
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String description;
}