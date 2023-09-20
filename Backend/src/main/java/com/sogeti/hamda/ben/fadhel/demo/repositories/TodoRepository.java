package com.sogeti.hamda.ben.fadhel.demo.repositories;


import com.sogeti.hamda.ben.fadhel.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findTodoByCategoryId(Long categoryId);
}
