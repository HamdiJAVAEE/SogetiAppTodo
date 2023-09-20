package com.sogeti.hamda.ben.fadhel.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {

    private Long id;

    private String title;

    private String description;

    private ZonedDateTime startDate;

    private boolean done;

    private boolean favorite;


    private CategoryDto category;

}
