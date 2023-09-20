package com.sogeti.hamda.ben.fadhel.demo.services.impl;


import com.sogeti.hamda.ben.fadhel.demo.dto.CategoryDto;
import com.sogeti.hamda.ben.fadhel.demo.dto.TodoDto;
import com.sogeti.hamda.ben.fadhel.demo.exception.EntityNotFoundException;
import com.sogeti.hamda.ben.fadhel.demo.exception.ErrorCodes;
import com.sogeti.hamda.ben.fadhel.demo.exception.InvalidEntityException;
import com.sogeti.hamda.ben.fadhel.demo.mapper.CategoryMapper;
import com.sogeti.hamda.ben.fadhel.demo.mapper.TodoMapper;
import com.sogeti.hamda.ben.fadhel.demo.model.Category;
import com.sogeti.hamda.ben.fadhel.demo.model.Todo;
import com.sogeti.hamda.ben.fadhel.demo.repositories.CategoryRepository;
import com.sogeti.hamda.ben.fadhel.demo.repositories.TodoRepository;
import com.sogeti.hamda.ben.fadhel.demo.services.TodoService;
import com.sogeti.hamda.ben.fadhel.demo.validators.TodoValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {


    private final TodoRepository todoRepository;


    private final CategoryRepository categoryRepository;


    private final TodoMapper todoMapper;

    private final CategoryMapper categoryMapper;

    @Override
    public TodoDto save(TodoDto todoDto) {
    List<String> errors = TodoValidator.validateTodo(todoDto);
        if (!errors.isEmpty()) {
            log.error("Todo is not valid {}", todoDto);
            throw new InvalidEntityException("Todo is not valid", ErrorCodes.TODO_NOT_VALID, errors);
        }
        return todoMapper.fromEntity(todoRepository.save(todoMapper.toEntity(todoDto)));
    }

    @Override
    public List<TodoDto> findAll() {
        return todoRepository.findAll().stream()
                .map(todoMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto findById(Long id) {
        if (id == null) {
            log.error("User id is null");
            return null;
        }
        final Long categoryId = categoryRepository.findCategoryByTodoId(id);
        Category category = new Category();
        category.setId(categoryId);

        final Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(value -> value.setCategory(category));

        final TodoDto todoDto = todoMapper.fromEntity(todo.get());
        CategoryDto categoryDto = categoryMapper.fromEntity(category);
        todoDto.setCategory(categoryDto);

        return Optional.of(todoDto).
                orElseThrow(() -> new EntityNotFoundException("No Todo found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public List<TodoDto> findByCategory(Long categoryId) {
        return todoRepository.findTodoByCategoryId(categoryId).stream()
                .sorted()
                .map(todoMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Todo id is null");
            return;
        }
        todoRepository.deleteById(id);
    }
}
