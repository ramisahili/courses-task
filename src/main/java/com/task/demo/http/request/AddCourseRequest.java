package com.task.demo.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCourseRequest {
    private String name;
    private int credits ;
}
