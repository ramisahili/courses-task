package com.task.demo.http.response;

import com.task.demo.entity.Student;
import com.task.demo.shared.dto.StudentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetStudentResponse {
    private List<StudentDto> students;
}
