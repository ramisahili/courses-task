package com.task.demo.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClassDetails {
    private String className ;
    private List<ClassDates> classDatesList ;
}
