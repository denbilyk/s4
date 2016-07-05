package com.company.service;

import com.company.config.OperationResult;
import com.company.dao.ClassRepository;
import com.company.dao.StudentRepository;
import com.company.dao.entity.ClassRecord;
import com.company.dao.entity.StudentRecord;
import com.company.web.dto.ClassDto;
import com.company.web.dto.StudentClassesDto;
import com.company.web.dto.StudentDto;
import com.google.common.collect.Collections2;
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
 *         Service to work with students
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private ClassRepository classRepository;

    /**
     * Method get full list of students without class assignment
     *
     * @return list of students
     */
    public Collection<StudentDto> findAll() {
        List<StudentRecord> studentRecords = repository.findAll();
        return Collections2.transform(studentRecords, student -> new StudentDto(student.getStudentId(), student.getFirstName(), student.getLastName()));
    }

    /**
     * Method return one student by student_id or null if student_id is not in database
     *
     * @param id is a student_id
     * @return student entry or null
     */
    public StudentDto findStudent(Long id) {
        StudentRecord one = repository.findOne(id);
        if (one == null) return null;
        return new StudentDto(one.getStudentId(), one.getFirstName(), one.getLastName());
    }

    /**
     * Method creates new entry of student
     *
     * @param params encapsulate new entry properties
     * @return OperationResult
     */
    public OperationResult createStudent(StudentDto params) {
        if (params.getStudentId() == null)
            return OperationResult.status(OperationResult.OperationStatus.STUDENT_INVALID).text("student_id");
        if (params.getFirstName() == null)
            return OperationResult.status(OperationResult.OperationStatus.STUDENT_INVALID).text("first_name");
        if (params.getLastName() == null)
            return OperationResult.status(OperationResult.OperationStatus.STUDENT_INVALID).text("last_name");
        StudentRecord record = new StudentRecord(params.getStudentId(), params.getFirstName(), params.getLastName());
        return save(record);
    }

    /**
     * Method update existing student entry
     *
     * @param id     is a student_id
     * @param params encapsulate updated properties
     * @return OperationResult
     */
    public OperationResult updateStudent(Long id, StudentDto params) {
        StudentRecord studentRecord = repository.findOne(id);
        if (studentRecord == null) return OperationResult.status(OperationResult.OperationStatus.STUDENT_NOT_FOUND);
        if (params.getFirstName() != null)
            studentRecord.setFirstName(params.getFirstName());
        if (params.getLastName() != null)
            studentRecord.setLastName(params.getLastName());
        return update(studentRecord);
    }

    /**
     * Method delete student entry by student_id
     *
     * @param id is a student_id
     * @return OperationResult
     */
    public OperationResult deleteStudent(Long id) {
        if (!repository.exists(id))
            return OperationResult.status(OperationResult.OperationStatus.STUDENT_NOT_FOUND).id(id);
        StudentRecord studentRecord = repository.findOne(id);
        if (studentRecord.getClasses().size() > 0)
            return OperationResult.status(OperationResult.OperationStatus.STUDENT_IN_CLASS).id(id);
        repository.delete(id);
        return OperationResult.status(OperationResult.OperationStatus.STUDENT_DELETED);
    }

    /**
     * Method returns student entries with linked classes
     *
     * @return list of found objects
     */
    public Collection<StudentClassesDto> getStudentAssignments() {
        List<StudentRecord> studentRecords = repository.findAll();
        return Collections2.transform(studentRecords, input -> new StudentClassesDto(input.getStudentId(), input.getFirstName(), input.getLastName(),
                Collections2.transform(input.getClasses(), item -> new ClassDto(item.getId(), item.getTitle(), item.getDescription()))
        ));
    }

    /**
     * Method performs search by student entries according to student properties
     *
     * @param studentId is a student_id
     * @param firstName is a student's first name
     * @param lastName  is a student's last name
     * @return list of found objects
     */
    public Collection<StudentDto> searchStudents(Long studentId, String firstName, String lastName) {
        List<StudentRecord> list = new ArrayList<>();
        if (studentId != null || firstName != null || lastName != null) {
            StudentRecord example = new StudentRecord();
            example.setStudentId(studentId);
            example.setFirstName(firstName);
            example.setLastName(lastName);
            list.addAll(repository.findAll(Example.of(example, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues())));
        }
        return Collections2.transform(list, student -> new StudentDto(student.getStudentId(), student.getFirstName(), student.getLastName()));
    }

    /**
     * Method assigns student to class
     *
     * @param id   is a student_id
     * @param code is a class code
     * @return OperationResult
     */
    public OperationResult assignStudent(Long id, Long code) {
        StudentRecord studentRecord = repository.findOne(id);
        ClassRecord classRecord = classRepository.findOne(code);

        if (studentRecord == null) return OperationResult.status(OperationResult.OperationStatus.STUDENT_NOT_FOUND);
        if (classRecord == null) return OperationResult.status(OperationResult.OperationStatus.CLASS_NOT_FOUND);
        studentRecord.assignToClass(classRecord);
        classRecord.assignStudent(studentRecord);
        classRepository.save(classRecord);
        repository.save(studentRecord);
        return OperationResult.status(OperationResult.OperationStatus.STUDENT_ASSIGNED);
    }

    /**
     * Method unassigns student from class
     *
     * @param id   is a student_id
     * @param code is a class code
     * @return OperationResult
     */
    public OperationResult unassignStudent(Long id, Long code) {
        StudentRecord studentRecord = repository.findOne(id);
        ClassRecord classRecord = classRepository.findOne(code);

        if (studentRecord == null) return OperationResult.status(OperationResult.OperationStatus.STUDENT_NOT_FOUND);
        if (classRecord == null) return OperationResult.status(OperationResult.OperationStatus.CLASS_NOT_FOUND);

        boolean result = studentRecord.unassignFromClass(classRecord);
        result &= classRecord.unassignStudent(studentRecord);
        if (!result) return OperationResult.status(OperationResult.OperationStatus.STUDENT_NOT_ASSIGNED);
        classRepository.save(classRecord);
        repository.save(studentRecord);
        return OperationResult.status(OperationResult.OperationStatus.STUDENT_UNASSIGNED);
    }

    private OperationResult save(StudentRecord record) {
        boolean exists = repository.exists(record.getStudentId());
        if (exists) {
            return OperationResult.status(OperationResult.OperationStatus.STUDENT_EXISTS).id(record.getStudentId());
        }
        return update(record);
    }

    private OperationResult update(StudentRecord record) {
        repository.save(record);
        return OperationResult.status(OperationResult.OperationStatus.STUDENT_CREATED).id(record.getStudentId());
    }
}
