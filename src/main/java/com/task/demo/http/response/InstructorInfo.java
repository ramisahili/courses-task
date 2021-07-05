package com.task.demo.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstructorInfo {

    private String name ;
    private List<ClassDetails> classDetailsList ;
}
