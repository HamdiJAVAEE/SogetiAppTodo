package com.sogeti.hamda.ben.fadhel.demo.mapper;


import com.sogeti.hamda.ben.fadhel.demo.dto.TodoDto;
import com.sogeti.hamda.ben.fadhel.demo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;


@Slf4j
@Service
@AllArgsConstructor
public class TodoMapper {

    private final CategoryMapper categoryMapper;

    public Todo toEntity(TodoDto todoDto) {
        final Todo todo = new Todo();
        todo.setId(todoDto.getId());
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setDone(todoDto.isDone());
        todo.setFavorite(todoDto.isFavorite());
        todo.setStartDate(ZonedDateTime.now());
        todo.setCategory(categoryMapper.toEntity(todoDto.getCategory()));

        return todo;
    }

    public  TodoDto fromEntity(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .startDate(todo.getStartDate())
                .done(todo.isDone())
                .favorite(todo.isFavorite())
                .build();
    }
}
