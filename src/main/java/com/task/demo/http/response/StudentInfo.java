package com.task.demo.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentInfo {
    private String firstname ;
    private String lastname ;
    private List<ClassDetails> classDetailsList ;
}
