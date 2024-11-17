package com.fullstackdevops.doctorstaffms.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StaffNotFoundException extends RuntimeException {
    private String message;
}
