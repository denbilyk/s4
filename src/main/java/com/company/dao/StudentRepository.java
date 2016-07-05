package com.company.dao;

import com.company.dao.entity.StudentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author denbilyk
 */
public interface StudentRepository extends JpaRepository<StudentRecord, Long> {

}
