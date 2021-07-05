package com.task.demo.repository;

import com.task.demo.entity.Course;
import com.task.demo.entity.Prerequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrerequisiteRepository extends JpaRepository<Prerequisite , Long> {
    List<Prerequisite> findByParentId(long id) ;
    List<Prerequisite> findByChildId(long id) ;
}
