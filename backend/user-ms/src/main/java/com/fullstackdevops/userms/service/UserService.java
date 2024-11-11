package com.fullstackdevops.userms.service;

import com.fullstackdevops.userms.dto.RegistrationDto;
import com.fullstackdevops.userms.dto.UserDto;

public interface UserService {

    UserDto createUser(RegistrationDto registrationDto);

    UserDto updateUser(Long id, UserDto userDto);

    UserDto getUserById(Long id);

    UserDto login(String username, String password);

}

