package com.task.demo.repository;

import com.task.demo.entity.ClassDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassDateRepository extends JpaRepository<ClassDate , Long> {
    List<ClassDate> findByClaId(long classId) ;
}
