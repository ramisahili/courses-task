package com.task.demo.http.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AddClassDateRequest {

    private long classId;
    private String day ;
    private LocalTime startTime;
    private LocalTime endTime;

}
