package com.task.demo.controller;

import com.task.demo.http.request.AddInstructorRequest;
import com.task.demo.http.response.GetInstructorsResponse;
import com.task.demo.service.InstructorService;
import com.task.demo.shared.dto.AddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService ;

    @PostMapping("/instructor")
    public AddingResponse add(@RequestBody AddInstructorRequest request){
       return instructorService.add(request);
    }
    @GetMapping("/instructors")
    public GetInstructorsResponse getAll(){
        return instructorService.getAll();
    }
}
