package com.sogeti.hamda.ben.fadhel.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String name;

    private String description;

    private UserDto user;

    private List<TodoDto> todoList;

}
