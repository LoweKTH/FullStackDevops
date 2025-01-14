package com.fullstackdevops.doctorstaffms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String socialSecurityNumber;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String specialty;
}
