package com.fullstackdevops.patientms.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String address;
    private String gender;
    private String email;
    private String phoneNumber;
    private List<Note> notes;
    private List<Encounter> encounters;
    private Set<Conditions> conditions;

}
