package com.fullstackdevops.userms.controller;


import com.fullstackdevops.userms.dto.LoginDto;
import com.fullstackdevops.userms.dto.RegistrationDto;
import com.fullstackdevops.userms.dto.UserDto;
import com.fullstackdevops.userms.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody RegistrationDto registrationDto) {
        return new ResponseEntity<>(userService.createUser(registrationDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {

        UserDto user = userService.login(loginDto.getUsername(), loginDto.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
    }
}
