package com.company.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author denbilyk
 * Class describes class entity
 */
@Entity
@Table(name = "class")
@EqualsAndHashCode(exclude = "students")
public class ClassRecord {

    /**
     * id is appropriate to 'code' field in REST service
     */
    @Id
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String description;
    @Version
    @Getter
    @Setter
    private Long version;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "classes")
    @Getter
    private Set<StudentRecord> students = new HashSet<>();


    public ClassRecord(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public ClassRecord() {
    }

    @Transient
    public boolean assignStudent(StudentRecord studentRecord) {
        if (students.contains(studentRecord)) return false;
        students.add(studentRecord);
        return true;
    }

    @Transient
    public boolean unassignStudent(StudentRecord studentRecord) {
        if (!students.contains(studentRecord)) return false;
        students.remove(studentRecord);
        return true;
    }
}
