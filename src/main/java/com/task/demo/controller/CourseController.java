package com.task.demo.controller;

import com.task.demo.http.request.AddCourseHistoryRequest;
import com.task.demo.http.request.AddCourseRequest;
import com.task.demo.http.request.AddPrerequisiteRequest;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.http.response.GetCoursesResponse;
import com.task.demo.service.CourseHistoryService;
import com.task.demo.service.CourseService;
import com.task.demo.service.PrerequisiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    private final CourseHistoryService courseHistoryService ;

    private final PrerequisiteService prerequisiteService ;

    @PostMapping("/course")
    public AddingResponse add(@RequestBody AddCourseRequest request){
        return courseService.add(request);
    }

    @GetMapping("/courses")
    public GetCoursesResponse getAll(){
        return courseService.getAll();
    }

    @PostMapping("/courseHistory")
    public AddingResponse add(@RequestBody AddCourseHistoryRequest request){
        return courseHistoryService.add(request);
    }
    @PostMapping("/prerequisite")
    public AddingResponse addingResponse(@RequestBody AddPrerequisiteRequest addPrerequisiteRequest){
        return prerequisiteService.addPrerequisite(addPrerequisiteRequest);
    }


}
