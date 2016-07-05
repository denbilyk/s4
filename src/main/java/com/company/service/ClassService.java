package com.company.service;

import com.company.config.OperationResult;
import com.company.dao.ClassRepository;
import com.company.dao.entity.ClassRecord;
import com.company.web.dto.ClassDto;
import com.company.web.dto.ClassStudentsDto;
import com.company.web.dto.StudentDto;
import com.google.common.collect.Collections2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author denbilyk
 *         <p>
 *         Service to work with classes
 */

@Slf4j
@Service
public class ClassService {

    @Autowired
    private ClassRepository repository;


    /**
     * Method gets full list of classes without linking students
     *
     * @return list of classes
     */
    public Collection<ClassDto> findAll() {
        List<ClassRecord> studentRecords = repository.findAll();
        return Collections2.transform(studentRecords, input -> new ClassDto(input.getId(), input.getTitle(), input.getDescription()));
    }

    /**
     * Method returns one class by class code or null if code is not in database
     *
     * @param id is a class code
     * @return class entry or null
     */
    public ClassDto findClass(Long id) {
        ClassRecord one = repository.findOne(id);
        if (one == null) return null;
        return new ClassDto(one.getId(), one.getTitle(), one.getDescription());
    }

    /**
     * Method creates new entry of class
     *
     * @param params encapsulate new entry properties
     * @return OperationResult
     */
    public OperationResult createClass(ClassDto params) {
        if (params.getId() == null)
            return OperationResult.status(OperationResult.OperationStatus.CLASS_INVALID).text("code");
        if (params.getTitle() == null)
            return OperationResult.status(OperationResult.OperationStatus.CLASS_INVALID).text("title");
        if (params.getDescription() == null)
            return OperationResult.status(OperationResult.OperationStatus.CLASS_INVALID).text("description");
        ClassRecord record = new ClassRecord(params.getId(), params.getTitle(), params.getDescription());
        return save(record);
    }

    /**
     * Method updates existing class entry
     *
     * @param id     is a class code
     * @param params encapsulate updated properties
     * @return OperationResult
     */
    public OperationResult updateClass(Long id, ClassDto params) {
        ClassRecord studentRecord = repository.findOne(id);
        if (params.getTitle() != null)
            studentRecord.setTitle(params.getTitle());
        if (params.getDescription() != null)
            studentRecord.setDescription(params.getDescription());
        return update(studentRecord);
    }

    /**
     * Method deletes class entry by class code
     *
     * @param id is a class code
     * @return OperationResult
     */
    public OperationResult deleteClass(Long id) {
        if (!repository.exists(id))
            return OperationResult.status(OperationResult.OperationStatus.CLASS_NOT_FOUND).id(id);
        if (repository.findOne(id).getStudents().size() > 0)
            return OperationResult.status(OperationResult.OperationStatus.CLASS_WITH_STUDENT).id(id);
        repository.delete(id);
        return OperationResult.status(OperationResult.OperationStatus.CLASS_DELETED);

    }

    /**
     * Method returns class entries with assigned students
     *
     * @return list of found objects
     */
    public Collection<ClassStudentsDto> getClassAssignments() {
        List<ClassRecord> classRecords = repository.findAll();
        return Collections2.transform(classRecords, clazz -> new ClassStudentsDto(clazz.getId(), clazz.getTitle(), clazz.getDescription(),
                Collections2.transform(clazz.getStudents(), student -> new StudentDto(student.getStudentId(), student.getFirstName(), student.getLastName()))
        ));
    }

    /**
     * Method performs search by class entries according to class properties
     *
     * @param code        is a class code
     * @param title       is a class title
     * @param description is a class description
     * @return list of found objects
     */
    public Collection<ClassDto> searchClass(Long code, String title, String description) {
        List<ClassRecord> list = new ArrayList<>();
        if (code != null || title != null || description != null) {
            ClassRecord example = new ClassRecord();
            example.setId(code);
            example.setTitle(title);
            example.setDescription(description);
            list.addAll(repository.findAll(Example.of(example, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues())));
        }
        return Collections2.transform(list, student -> new ClassDto(student.getId(), student.getTitle(), student.getDescription()));
    }

    private OperationResult save(ClassRecord record) {
        if (record.getId() != null) {
            boolean exists = repository.exists(record.getId());
            if (exists) {
                return OperationResult.status(OperationResult.OperationStatus.CLASS_EXISTS).id(record.getId());
            }
        }
        return update(record);
    }

    private OperationResult update(ClassRecord record) {
        repository.save(record);
        return OperationResult.status(OperationResult.OperationStatus.CLASS_CREATED).id(record.getId());
    }

}
