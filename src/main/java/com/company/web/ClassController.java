package com.company.web;

import com.company.config.OperationResult;
import com.company.service.ClassService;
import com.company.web.dto.ClassDto;
import com.company.web.dto.ClassStudentsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author denbilyk
 */
@RestController
@RequestMapping("/class")
@Slf4j
public class ClassController {

    @Autowired
    private ClassService service;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<ClassDto>> listClasses() {
        log.trace("Request all existing classes");
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ResponseEntity<ClassDto> findClass(@PathVariable Long code) {
        log.trace("Request class by code: " + code);
        ClassDto student = service.findClass(code);
        return ResponseEntity.ok(student);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OperationResult> create(@RequestBody ClassDto parameters) {
        log.trace("Request to create new class: " + parameters);
        OperationResult operationResult = service.createClass(parameters);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.PUT)
    public ResponseEntity<OperationResult> update(@PathVariable("code") Long code, @RequestBody ClassDto params) {
        log.trace("Request to update class with code: " + code + " Parameters: " + params);
        OperationResult operationResult = service.updateClass(code, params);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
    public ResponseEntity<OperationResult> delete(@PathVariable("code") Long code) {
        log.trace("Request to delete class with code: " + code);
        OperationResult operationResult = service.deleteClass(code);
        return ResponseEntity.status(operationResult.getStatus().getHttpCode()).body(operationResult);
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<Collection<ClassStudentsDto>> getClassAssignments() {
        log.trace("Request class assignments");
        return ResponseEntity.ok(service.getClassAssignments());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Collection<ClassDto>> searchClass(
            @RequestParam(value = "code", required = false) Long code,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description) {
        log.trace("Request to search - code: " + code + " title: " + title + " description: " + description);
        Collection<ClassDto> classDtos = service.searchClass(code, title, description);
        return ResponseEntity.ok(classDtos);
    }
}
