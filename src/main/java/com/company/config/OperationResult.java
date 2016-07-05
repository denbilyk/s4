package com.company.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

/**
 * @author denbilyk
 */

public class OperationResult {
    private static OperationResult instance;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private String text;
    @Getter
    private OperationStatus status;

    private OperationResult(OperationStatus status) {
        this.status = status;
    }

    public static OperationResult status(OperationResult.OperationStatus status) {
        instance = new OperationResult(status);
        instance.setText(status.getValue());
        return instance;
    }

    public OperationResult id(long code) {
        instance.setId(code);
        return instance;
    }

    public OperationResult text(String text) {
        instance.setText(text);
        return instance;
    }

    public enum OperationStatus {
        STUDENT_CREATED("Student created", 201), STUDENT_EXISTS("Student with same id has already exist!", 202),
        STUDENT_DELETED("Student deleted", 200), STUDENT_NOT_FOUND("Student with id not found!", 202),
        STUDENT_INVALID("Miss parameter!", 400), STUDENT_IN_CLASS("Student is linked with class!", 202),
        STUDENT_ASSIGNED("Student successfully assigned", 201), STUDENT_UNASSIGNED("Student successfully unassigned", 201),
        STUDENT_NOT_ASSIGNED("Student wasn't assigned", 202),

        CLASS_CREATED("Class created", 201), CLASS_EXISTS("Class with same id has already exist!", 202),
        CLASS_DELETED("Class deleted", 200), CLASS_NOT_FOUND("Class with id not found!", 202),
        CLASS_WITH_STUDENT("Class is linked with student!", 202), CLASS_INVALID("Miss parameter!", 400);

        @Getter
        private String value;

        @Getter
        @Transient
        private int httpCode;

        OperationStatus(String value, int httpCode) {
            this.value = value;
            this.httpCode = httpCode;
        }
    }
}
