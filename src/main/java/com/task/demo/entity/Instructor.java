package com.task.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "instructor")
@Getter
@Setter
public class Instructor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "instructor_name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "instructor")
    private Set<Class> classes;
}
