package com.task.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "class")
public class Class {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cla")
    private Set<ClassDate> classDates;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cla")
    private Set<StudentClass> studentClasses;


}

