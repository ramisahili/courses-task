package com.task.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "prerequisites")
@Getter
@Setter
public class Prerequisite {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Course parent;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Course  child;
}
