package com.sogeti.hamda.ben.fadhel.demo.services;



import com.sogeti.hamda.ben.fadhel.demo.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto user);

    List<UserDto> findAll();

    UserDto findById(Long id);

    void delete(Long id);

    UserDto login(UserDto user);
}
