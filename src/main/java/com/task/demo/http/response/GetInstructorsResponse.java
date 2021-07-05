package com.task.demo.http.response;

import com.task.demo.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetInstructorsResponse {
    private List<InstructorInfo> instructorInfoList;
}
