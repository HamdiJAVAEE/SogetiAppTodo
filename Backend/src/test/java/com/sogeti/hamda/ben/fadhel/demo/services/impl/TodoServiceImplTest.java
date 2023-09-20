package com.sogeti.hamda.ben.fadhel.demo.services.impl;

import com.sogeti.hamda.ben.fadhel.demo.dto.CategoryDto;
import com.sogeti.hamda.ben.fadhel.demo.dto.TodoDto;

import com.sogeti.hamda.ben.fadhel.demo.mapper.CategoryMapper;
import com.sogeti.hamda.ben.fadhel.demo.mapper.TodoMapper;
import com.sogeti.hamda.ben.fadhel.demo.model.Category;
import com.sogeti.hamda.ben.fadhel.demo.model.Todo;
import com.sogeti.hamda.ben.fadhel.demo.repositories.CategoryRepository;
import com.sogeti.hamda.ben.fadhel.demo.repositories.TodoRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl todoService;

    @Mock
    private TodoRepository todoRepository;


    @Mock
    private TodoMapper todoMapper;


    @Mock
    private CategoryRepository categoryRepository;



    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void GIVEN_todoListInDatabase_WHEN_findAll_THEN_returnTodoDtoList() {

        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Todo 1");

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Todo 2");

        List<Todo> todoList = Arrays.asList(todo1, todo2);

        when(todoRepository.findAll()).thenReturn(todoList);

        TodoDto todoDto1 = new TodoDto();
        todoDto1.setId(1L);
        todoDto1.setTitle("Todo 1 DTO");

        TodoDto todoDto2 = new TodoDto();
        todoDto2.setId(2L);
        todoDto2.setTitle("Todo 2 DTO");

        List<TodoDto> expectedTodoDtoList = Arrays.asList(todoDto1, todoDto2);

        when(todoMapper.fromEntity(any(Todo.class))).thenAnswer(invocation -> {
            Todo todo = invocation.getArgument(0);
            if (todo.getId() == 1L) return todoDto1;
            if (todo.getId() == 2L) return todoDto2;
            return null;
        });


        List<TodoDto> result = todoService.findAll();


        assertEquals(expectedTodoDtoList.size(), result.size());
        for (int i = 0; i < expectedTodoDtoList.size(); i++) {
            assertEquals(expectedTodoDtoList.get(i).getId(), result.get(i).getId());
            assertEquals(expectedTodoDtoList.get(i).getTitle(), result.get(i).getTitle());
        }

        verify(todoRepository, times(1)).findAll();
        verify(todoMapper, times(2)).fromEntity(any(Todo.class));
    }

    @Test
    public void GIVEN_noTodoListInDatabase_WHEN_findAll_THEN_returnEmptyTodoDtoList() {

        when(todoRepository.findAll()).thenReturn(Collections.emptyList());


        List<TodoDto> result = todoService.findAll();


        assertTrue(result.isEmpty());

        verify(todoRepository, times(1)).findAll();
        verifyZeroInteractions(todoMapper);
    }


    @Test
    public void testFindByIdWithValidId() {
        Long todoId = 1L;
        Long categoryId = 2L;


        Todo todoEntity = new Todo();
        todoEntity.setId(todoId);


        Category categoryEntity = new Category();
        categoryEntity.setId(categoryId);


        TodoDto expectedTodoDto = new TodoDto();
        expectedTodoDto.setId(todoId);


        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todoEntity));
        when(categoryRepository.findCategoryByTodoId(todoId)).thenReturn(categoryId);
        when(todoMapper.fromEntity(todoEntity)).thenReturn(expectedTodoDto);
        when(categoryMapper.fromEntity(categoryEntity)).thenReturn(new CategoryDto());


        TodoDto result = todoService.findById(todoId);


        assertNotNull(result);
        assertEquals(expectedTodoDto.getId(), result.getId());
    }

    @Test
    public void GIVEN_validTodoDto_WHEN_save_THEN_returnSavedTodoDto() {

        TodoDto inputTodoDto = new TodoDto();
        inputTodoDto.setTitle("Valid Todo");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        categoryDto.setName("Category ");
        categoryDto.setDescription("description ");
        inputTodoDto.setCategory(categoryDto);

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Valid Todo");

        when(todoMapper.toEntity(inputTodoDto)).thenReturn(todo);
        when(todoRepository.save(todo)).thenReturn(todo);

        TodoDto expectedSavedTodoDto = new TodoDto();
        expectedSavedTodoDto.setId(1L);
        expectedSavedTodoDto.setTitle("Valid Todo");

        when(todoMapper.fromEntity(todo)).thenReturn(expectedSavedTodoDto);


        TodoDto result = todoService.save(inputTodoDto);


        assertEquals(expectedSavedTodoDto.getId(), result.getId());
        assertEquals(expectedSavedTodoDto.getTitle(), result.getTitle());

        verify(todoMapper, times(1)).toEntity(inputTodoDto);
        verify(todoRepository, times(1)).save(todo);
        verify(todoMapper, times(1)).fromEntity(todo);
    }
}