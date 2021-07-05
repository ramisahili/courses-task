package com.task.demo.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddClassRequest {
    private long courseId;
    private long instructorId;
}
