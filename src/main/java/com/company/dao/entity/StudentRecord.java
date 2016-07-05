package com.company.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author denbilyk
 * Class describes student entity
 */
@Entity
@Table(name = "student")
@EqualsAndHashCode(exclude = "classes")
public class StudentRecord {

    @Id
    @Getter
    @Setter
    private Long studentId;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String firstName;
    @Version
    @Getter
    @Setter
    private Long version;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_class", joinColumns = {
            @JoinColumn(name = "student_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "class_id",
                    nullable = false, updatable = false)})
    @Getter
    private Set<ClassRecord> classes = new HashSet<>();


    public StudentRecord() {
    }

    public StudentRecord(Long studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Transient
    public boolean assignToClass(ClassRecord classRecord) {
        if (classes.contains(classRecord))
            return false;
        classes.add(classRecord);
        return true;
    }

    @Transient
    public boolean unassignFromClass(ClassRecord classRecord) {
        if (!classes.contains(classRecord)) return false;
        classes.remove(classRecord);
        return true;
    }
}
