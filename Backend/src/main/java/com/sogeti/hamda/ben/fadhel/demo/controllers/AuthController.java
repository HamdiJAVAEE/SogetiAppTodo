package com.sogeti.hamda.ben.fadhel.demo.controllers;

import com.sogeti.hamda.ben.fadhel.demo.controllers.api.AuthApi;
import com.sogeti.hamda.ben.fadhel.demo.dto.UserDto;
import com.sogeti.hamda.ben.fadhel.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController implements AuthApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<UserDto> loginUser(UserDto user) {
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }
}
