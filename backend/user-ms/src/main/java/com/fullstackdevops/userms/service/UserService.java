package com.fullstackdevops.userms.service;

import com.fullstackdevops.userms.dto.UserDto;

public interface UserService {

    public UserDto createUser(UserDto user);

    public UserDto updateUser(Long id, UserDto userDto);

    public UserDto getUserById(Long id);
}

