package com.company.dao;

import com.company.dao.entity.ClassRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author denbilyk
 */
public interface ClassRepository extends JpaRepository<ClassRecord, Long> {
}
