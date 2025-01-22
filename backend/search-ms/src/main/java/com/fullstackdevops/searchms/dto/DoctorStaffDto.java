package com.fullstackdevops.searchms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;


@RegisterForReflection
public class DoctorStaffDto {
    @JsonProperty("doctors")
    private List<DoctorDto> doctors;
    @JsonProperty("staffs")
    private List<StaffDto> staffs;

    public DoctorStaffDto(List<DoctorDto> doctors, List<StaffDto> staffs) {
        this.doctors = doctors;
        this.staffs = staffs;
    }

    public DoctorStaffDto(){

    }

    public List<DoctorDto> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDto> doctors) {
        this.doctors = doctors;
    }

    public List<StaffDto> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<StaffDto> staffs) {
        this.staffs = staffs;
    }

    @Override
    public String toString() {
        return "DoctorStaffDto{" +
                "doctors=" + doctors +
                ", staffs=" + staffs +
                '}';
    }
}