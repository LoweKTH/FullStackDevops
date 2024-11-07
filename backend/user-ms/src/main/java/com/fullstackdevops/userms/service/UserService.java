package com.fullstackdevops.userms.service;

import com.fullstackdevops.userms.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto updateUser(Long id, UserDto userDto);

    UserDto getUserById(Long id);

    UserDto login(String username, String password);

}

