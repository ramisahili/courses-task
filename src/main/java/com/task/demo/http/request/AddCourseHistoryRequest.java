package com.task.demo.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCourseHistoryRequest {
    private long studentId;
    private long courseId;
    private float grade ;
}

