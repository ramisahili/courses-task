package com.task.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student_class")
@Getter
@Setter
public class StudentClass {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class cla;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;


}
