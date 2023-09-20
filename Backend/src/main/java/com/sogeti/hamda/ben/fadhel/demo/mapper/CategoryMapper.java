package com.sogeti.hamda.ben.fadhel.demo.mapper;


import com.sogeti.hamda.ben.fadhel.demo.dto.CategoryDto;
import com.sogeti.hamda.ben.fadhel.demo.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryMapper {

    @Autowired
    private  UserMapper userMapper;

    @Autowired
    private  TodoMapper todoMapper;

    public Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();

        category.setUser(userMapper.toEntity(categoryDto.getUser()));
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return category;
    }

    public  CategoryDto fromEntity(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .todoList(
                        category.getTodoList() != null ? category.getTodoList().stream().map(todoMapper::fromEntity).collect(Collectors.toList()) : null
                )
                .build();
    }
}
