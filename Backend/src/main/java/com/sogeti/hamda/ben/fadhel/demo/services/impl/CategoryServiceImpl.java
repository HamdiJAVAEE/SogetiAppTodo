package com.sogeti.hamda.ben.fadhel.demo.services.impl;

import com.sogeti.hamda.ben.fadhel.demo.dto.CategoryDto;
import com.sogeti.hamda.ben.fadhel.demo.dto.TodoDto;
import com.sogeti.hamda.ben.fadhel.demo.exception.EntityNotFoundException;
import com.sogeti.hamda.ben.fadhel.demo.exception.ErrorCodes;
import com.sogeti.hamda.ben.fadhel.demo.exception.InvalidEntityException;
import com.sogeti.hamda.ben.fadhel.demo.mapper.CategoryMapper;
import com.sogeti.hamda.ben.fadhel.demo.repositories.CategoryRepository;
import com.sogeti.hamda.ben.fadhel.demo.services.CategoryService;
import com.sogeti.hamda.ben.fadhel.demo.validators.CategoryValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CategoryDto category) {
        List<String> errors = CategoryValidator.validateCategory(category);
        if (!errors.isEmpty()) {
            log.error("Category is not valid {}", category);
            throw new InvalidEntityException("Category is not valid", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return categoryMapper.fromEntity(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {
        if (id == null) {
            log.error("Category id is null");
            return null;
        }
        return categoryRepository.findById(id).map(categoryMapper::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No Category found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public List<CategoryDto> findAllByUserId(Long userId) {
        List<CategoryDto> categoryDtos = categoryRepository.findCategoryByUserId(userId).stream()
                .map(categoryMapper::fromEntity)
                .collect(Collectors.toList());

        categoryDtos.forEach(categoryDto -> {
            List<TodoDto> sortedTodoList = categoryDto.getTodoList().stream()
                    .sorted(Comparator.comparing(TodoDto::getStartDate).reversed())
                    .collect(Collectors.toList());

            categoryDto.setTodoList(sortedTodoList);
        });

        return categoryDtos;
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Category id is null");
            return;
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getAllTodoByCategoriesForToday(Long userId) {
        return categoryRepository.getAllTodoByCategoriesForToday(ZonedDateTime.now().withHour(0).withMinute(0),
                ZonedDateTime.now().withHour(23).withMinute(59), userId)
                .stream()
                .map(categoryMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
