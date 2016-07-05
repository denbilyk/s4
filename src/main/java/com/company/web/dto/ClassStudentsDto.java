package com.company.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;

/**
 * @author denbilyk
 */
@EqualsAndHashCode(callSuper = true)
public class ClassStudentsDto extends ClassDto {

    @Getter
    private Collection<StudentDto> students;

    public ClassStudentsDto(Long code, String title, String description, Collection<StudentDto> students) {
        super(code, title, description);
        this.students = students;
    }
}
