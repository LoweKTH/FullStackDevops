package com.fullstackdevops.patientms.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;
    /*private List<Encounter> encounters;
    private Set<Conditions> conditions;*/

}
