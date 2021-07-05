package com.task.demo.repository;

import com.task.demo.entity.CourseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseHistoryRepository extends JpaRepository<CourseHistory , Long> {
    List<CourseHistory> findByStudentId(long id) ;
}
