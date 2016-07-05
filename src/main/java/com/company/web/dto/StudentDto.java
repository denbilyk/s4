package com.company.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author denbilyk
 */
@EqualsAndHashCode
@ToString
public class StudentDto {

    @JsonProperty(value = "student_id")
    @Getter
    @Setter
    private Long studentId;

    @JsonProperty(value = "first_name")
    @Getter
    @Setter
    private String firstName;

    @JsonProperty(value = "last_name")
    @Getter
    @Setter
    private String lastName;

    public StudentDto(Long studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StudentDto() {
    }
}
