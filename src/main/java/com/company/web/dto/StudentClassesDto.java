package com.company.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;

/**
 * @author denbilyk
 */
@EqualsAndHashCode(callSuper = true)
public class StudentClassesDto extends StudentDto {

    @Getter
    private Collection<ClassDto> classes;


    public StudentClassesDto(Long studentId, String firstName, String lastName, Collection<ClassDto> classes) {
        super(studentId, firstName, lastName);
        this.classes = classes;
    }
}
