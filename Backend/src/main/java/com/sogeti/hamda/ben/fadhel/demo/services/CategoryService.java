package com.sogeti.hamda.ben.fadhel.demo.services;



import com.sogeti.hamda.ben.fadhel.demo.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto category);

    List<CategoryDto> findAll();

    CategoryDto findById(Long id);

    List<CategoryDto> findAllByUserId(Long userId);

    void delete(Long id);

    List<CategoryDto> getAllTodoByCategoriesForToday(Long userId);
}
