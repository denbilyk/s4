package com.company.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author denbilyk
 */
@EqualsAndHashCode
@ToString
public class ClassDto {

    @Getter
    @JsonProperty(value = "code")
    private Long id;
    @Getter
    private String title;
    @Getter
    private String description;

    public ClassDto(Long code, String title, String description) {
        this.id = code;
        this.description = description;
        this.title = title;
    }

    public ClassDto() {
    }
}
