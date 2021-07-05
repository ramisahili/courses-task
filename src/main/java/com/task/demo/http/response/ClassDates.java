package com.task.demo.http.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ClassDates {
    private String day;
    private LocalTime startTime ;
    private LocalTime endTime ;
}
