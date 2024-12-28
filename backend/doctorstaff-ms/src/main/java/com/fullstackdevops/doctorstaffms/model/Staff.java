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
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long socialSecurityNumber;
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String description;
}
