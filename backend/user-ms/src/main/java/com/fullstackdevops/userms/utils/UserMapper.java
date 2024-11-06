package com.fullstackdevops.userms.utils;

import com.fullstackdevops.userms.dto.UserDto;
import com.fullstackdevops.userms.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        if(user == null) return null;

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }

    public static User toEntity(UserDto dto) {
        if(dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }
}
