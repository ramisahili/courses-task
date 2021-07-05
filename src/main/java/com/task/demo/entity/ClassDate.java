package com.task.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "class_date")
public class ClassDate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name= "class_id", nullable=false)
    private Class cla;

    @Column(name = "day" , nullable = false)
    private String day ;

    @Column(name = "start_time" , nullable = false)
    private LocalTime startTime ;

    @Column(name = "end_time" , nullable = false)
    private LocalTime endTime   ;


}
