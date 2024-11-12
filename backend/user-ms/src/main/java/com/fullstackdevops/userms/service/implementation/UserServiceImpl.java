package com.fullstackdevops.userms.service.implementation;

import com.fullstackdevops.userms.dto.*;
import com.fullstackdevops.userms.repository.UserRepository;
import com.fullstackdevops.userms.service.UserService;
import com.fullstackdevops.userms.utils.UserExistsException;
import com.fullstackdevops.userms.utils.UserMapper;
import com.fullstackdevops.userms.model.User;
import com.fullstackdevops.userms.utils.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;


    @Override
    public UserDto createUser(RegistrationDto registrationDto){

        UserDto userDto = registrationDto.getUserDetails();
        AdditionalInfoDto additionalInfoDto = registrationDto.getAdditionalInfoDetails();
        System.out.println(additionalInfoDto.toString());

        if(userRepository.existsByUsername(userDto.getUsername()) || userRepository.existsByEmail(userDto.getEmail())){
            throw new UserExistsException("This username or email is already taken");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        User user = UserMapper.toEntity(userDto);
        userRepository.save(user);
        userDto = UserMapper.toDto(user);

        if(userDto.getRole().equalsIgnoreCase("patient")){
            String patientServiceEndpoint = "http://patient-ms:8080/api/patients/addPatient";

            PatientDto patientDto = new PatientDto();
            patientDto.setUserId(userDto.getId());
            patientDto.setEmail(userDto.getEmail());
            patientDto.setSocialSecurityNumber(additionalInfoDto.getSocialSecurityNumber());
            patientDto.setFirstname(additionalInfoDto.getFirstname());
            patientDto.setLastname(additionalInfoDto.getLastname());
            patientDto.setPhoneNumber(additionalInfoDto.getPhoneNumber());
            patientDto.setAddress(additionalInfoDto.getAddress());
            patientDto.setGender(additionalInfoDto.getGender());
            patientDto.setDateOfBirth(additionalInfoDto.getDateOfBirth());

            try {
                restTemplate.postForEntity(patientServiceEndpoint, patientDto, PatientDto.class);
            } catch (Exception e) {
                System.out.println("Failed to create patient in Patient MS: " + e.getMessage());
            }
        }else if(userDto.getRole().equalsIgnoreCase("doctor")){
            String doctorServiceEndpoint = "http://doctorstaff-ms:8080/api/doctors/addDoctor";

            DoctorDto doctorDto = new DoctorDto();
            doctorDto.setSocialSecurityNumber(additionalInfoDto.getSocialSecurityNumber());
            doctorDto.setUserId(userDto.getId());
            doctorDto.setEmail(userDto.getEmail());
            doctorDto.setFirstname(additionalInfoDto.getFirstname());
            doctorDto.setLastname(additionalInfoDto.getLastname());
            doctorDto.setSpecialty(additionalInfoDto.getSpecialty());
            doctorDto.setPhoneNumber(additionalInfoDto.getPhoneNumber());


            try {
                restTemplate.postForEntity(doctorServiceEndpoint, doctorDto, DoctorDto.class);
            } catch (Exception e) {
                System.out.println("Failed to create doctor in Doctor MS: " + e.getMessage());
            }
        }


        return userDto;
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with given id: " + id + " was not found"
                ));

        existingUser.setEmail(userDto.getEmail());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setRole(userDto.getRole());

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User with given id: " + id + " was not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Wrong username or password"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("Wrong username or password");
        }

        return UserMapper.toDto(user);

    }

}
