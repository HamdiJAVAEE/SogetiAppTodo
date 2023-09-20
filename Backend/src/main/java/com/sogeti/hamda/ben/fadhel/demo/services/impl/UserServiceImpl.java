package com.sogeti.hamda.ben.fadhel.demo.services.impl;


import com.sogeti.hamda.ben.fadhel.demo.dto.UserDto;
import com.sogeti.hamda.ben.fadhel.demo.exception.EntityNotFoundException;
import com.sogeti.hamda.ben.fadhel.demo.exception.ErrorCodes;
import com.sogeti.hamda.ben.fadhel.demo.exception.InvalidEntityException;
import com.sogeti.hamda.ben.fadhel.demo.mapper.UserMapper;
import com.sogeti.hamda.ben.fadhel.demo.repositories.UserRepository;
import com.sogeti.hamda.ben.fadhel.demo.services.UserService;
import com.sogeti.hamda.ben.fadhel.demo.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto save(UserDto user) {
        List<String> errors = UserValidator.validateUser(user);
        if (!errors.isEmpty()) {
            log.error("User is not valid {}", user);
            throw new InvalidEntityException("User is not valid", ErrorCodes.USER_NOT_VALID, errors);
        }
        return userMapper.fromEntity(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        if (id == null) {
            log.error("User id is null");
            return null;
        }
        return userRepository.findById(id).map(userMapper::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("User id is null");
            throw new EntityNotFoundException("No user found with ID = " + id, ErrorCodes.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto login(UserDto user) {
        List<String> errors = UserValidator.validateUserCredentials(user.getEmail(), user.getPassword());
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("User is not valid", ErrorCodes.USER_NOT_VALID, errors);
        }
        return userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword())
                .map(userMapper::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user found with Email = " + user.getEmail() + " and Password = <HIDDEN_PASSWORD>", ErrorCodes.USER_NOT_FOUND));
    }
}
