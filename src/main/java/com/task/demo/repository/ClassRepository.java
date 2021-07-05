package com.task.demo.repository;

import com.task.demo.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    List<Class> findByCourseId(long id) ;
    List<Class> findByInstructorId(long id) ;
}


