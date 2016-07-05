package com.company.web;

import com.company.config.OperationResult;
import com.company.service.StudentService;
import com.company.web.dto.StudentClassesDto;
import com.company.web.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author denbilyk
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService service;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<StudentDto>> listStudents() {
        log.trace("Request all existing students");
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<StudentDto> findStudent(@PathVariable Long id) {
        log.trace("Request student by student_id: " + id);
        StudentDto student = service.findStudent(id);
        return ResponseEntity.ok(student);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OperationResult> create(@RequestBody StudentDto parameters) {
        log.trace("Request to create new student: " + parameters);
        OperationResult operationResult = service.createStudent(parameters);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<OperationResult> update(@PathVariable("id") Long id, @RequestBody StudentDto params) {
        log.trace("Request to update student with student_id: " + id + " Parameters: " + params);
        OperationResult operationResult = service.updateStudent(id, params);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<OperationResult> delete(@PathVariable("id") Long id) {
        log.trace("Request to delete student with student_id: " + id);
        OperationResult operationResult = service.deleteStudent(id);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public ResponseEntity<Collection<StudentClassesDto>> getStudentsAssignments() {
        log.trace("Request student assignments");
        return ResponseEntity.ok(service.getStudentAssignments());
    }

    @RequestMapping(value = "/{id}/class/{code}", method = RequestMethod.PUT)
    public ResponseEntity<OperationResult> assignToClass(@PathVariable("id") Long id, @PathVariable("code") Long code) {
        log.trace("Request to assign student to class - student_id: " + id + " code: " + code);
        OperationResult operationResult = service.assignStudent(id, code);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/{id}/class/{code}/unassign", method = RequestMethod.PUT)
    public ResponseEntity<OperationResult> unassignFromClass(@PathVariable("id") Long id, @PathVariable("code") Long code) {
        log.trace("Request to unassign student from class - student_id: " + id + " code: " + code);
        OperationResult operationResult = service.unassignStudent(id, code);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Collection<StudentDto>> searchStudents(
            @RequestParam(value = "student_id", required = false) Long id,
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName
    ) {
        log.trace("Request to search - student_id: " + id + " first_name: " + firstName + " last_name: " + lastName);
        Collection<StudentDto> searchStudent = service.searchStudents(id, firstName, lastName);
        return ResponseEntity.ok(searchStudent);
    }
}
