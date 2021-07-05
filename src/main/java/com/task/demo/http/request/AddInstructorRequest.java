package com.task.demo.http.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.SecondaryTable;

@Getter
@Setter
public class AddInstructorRequest {

    private String instructorName;
}
