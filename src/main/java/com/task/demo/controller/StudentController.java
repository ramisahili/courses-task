package com.task.demo.controller;

import com.task.demo.http.request.AddStudentClassRequest;
import com.task.demo.http.request.AddStudentRequest;
import com.task.demo.http.response.GetStudentResponse;
import com.task.demo.http.response.GetStudentsDetailsResponse;
import com.task.demo.service.StudentClassService;
import com.task.demo.service.StudentService;
import com.task.demo.shared.dto.AddingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService ;
    private final StudentClassService studentClassService;

    @PostMapping(value = "/student")
    public AddingResponse add(@RequestBody AddStudentRequest request){
        return studentService.add(request);
    }

    @GetMapping(value = "/students")
    public GetStudentResponse getAll(){
        return studentService.getAll();
    }
    @PostMapping(value = "/enroll")
    public AddingResponse enroll(@RequestBody AddStudentClassRequest request){
        return studentClassService.add(request);
    }
    @GetMapping(value = "/studentsDetails")
    public GetStudentsDetailsResponse getAllDetails(){
        return studentClassService.getAll();
    }

}
