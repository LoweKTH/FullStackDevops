package com.fullstackdevops.userms.service.implementation;

import com.fullstackdevops.userms.repository.UserRepository;
import com.fullstackdevops.userms.service.UserService;
import com.fullstackdevops.userms.dto.UserDto;
import com.fullstackdevops.userms.utils.UserExistsException;
import com.fullstackdevops.userms.utils.UserMapper;
import com.fullstackdevops.userms.model.User;
import com.fullstackdevops.userms.utils.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto userDto){
        if(userRepository.existsByUsername(userDto.getUsername()) || userRepository.existsByEmail(userDto.getEmail())){
            throw new UserExistsException("This username or email is already taken");
        }

        User user = UserMapper.toEntity(userDto);
        userRepository.save(user);
        userDto = UserMapper.toDto(user);
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


}
