package com.task.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "course_name", nullable = false)
    private String name;

    @Column(name = "number_of_credits", nullable = false)
    private int credits;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private Set<Class> classes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<Prerequisite> prerequisites;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private Set<CourseHistory> courseHistory;
}