package com.sogeti.hamda.ben.fadhel.demo.validators;


import com.sogeti.hamda.ben.fadhel.demo.dto.TodoDto;

import java.util.ArrayList;
import java.util.List;

public class TodoValidator {

    public static List<String> validateTodo(TodoDto todoDto) {
        List<String> errors = new ArrayList<>();
        if (todoDto == null) {
            errors.add("Please fill the title");
            errors.add("Please select a category");
            return errors;
        }
        if (todoDto.getTitle() == null || todoDto.getTitle().isBlank()) {
            errors.add("Please fill the title");
        }
        if (todoDto.getCategory() == null || todoDto.getCategory().getId() == null) {
            errors.add("Please select a category");
        }
        return errors;
    }
}
