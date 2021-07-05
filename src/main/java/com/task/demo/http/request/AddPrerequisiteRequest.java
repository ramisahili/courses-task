package com.task.demo.http.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPrerequisiteRequest {

    private long courseId;
    private long prerequisiteId ;
}
