package com.sogeti.hamda.ben.fadhel.demo.mapper;




import com.sogeti.hamda.ben.fadhel.demo.dto.UserDto;
import com.sogeti.hamda.ben.fadhel.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service

public class UserMapper {

    @Autowired
    private  CategoryMapper categoryMapper;

    public User toEntity(UserDto userDto) {
        final User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCategory(
                userDto.getCategory() != null ? userDto.getCategory().stream().map(categoryMapper::toEntity).collect(Collectors.toList()) : null
        );

        return user;
    }

    public  UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .category(
                        user.getCategory() != null ? user.getCategory().stream().map(categoryMapper::fromEntity).collect(Collectors.toList()) : null
                )
                .build();
    }


}
