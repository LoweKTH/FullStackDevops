package com.fullstackdevops.doctorstaffms.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DoctorNotFoundException extends RuntimeException {
    private String message;
}
