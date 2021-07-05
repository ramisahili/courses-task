package com.task.demo.controller;

import com.task.demo.http.request.AddClassDateRequest;
import com.task.demo.http.request.AddClassRequest;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.service.ClassDateService;
import com.task.demo.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;
    private final ClassDateService classDateService;


    @PostMapping("/class")
    public AddingResponse add(@RequestBody AddClassRequest addClassRequest){
        return classService.addClass(addClassRequest);
    }

    @PostMapping("/classDate")
    public AddingResponse addingResponse(@RequestBody AddClassDateRequest request){
        return classDateService.add(request);
    }
}
