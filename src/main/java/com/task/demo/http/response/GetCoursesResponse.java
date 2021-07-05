package com.task.demo.http.response;

import com.task.demo.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCoursesResponse {
    private List<String> courses;
}
