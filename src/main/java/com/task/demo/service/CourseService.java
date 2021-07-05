package com.task.demo.service;

import com.task.demo.shared.helper.Translator;
import com.task.demo.entity.Course;
import com.task.demo.http.request.AddCourseRequest;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.http.response.GetCoursesResponse;
import com.task.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CourseService {


    private final CourseRepository courseRepository;

    private final Translator translator;

    public AddingResponse add(AddCourseRequest request){
        log.info("Adding course ,{}" , request.getName());
        AddingResponse response = new AddingResponse();
        try
        {
            Course courseToAdd = courseRepository.findByName(request.getName());
            if(courseToAdd == null){
                courseToAdd = new Course();
                courseToAdd.setName(request.getName());
                courseRepository.save(courseToAdd);
                response.setResult(translator.toLocale("s1"));
                response.setDescription(translator.toLocale("s2"));
                log.info("course added successfully");
                return response;
            }
            response.setDescription(translator.toLocale("s3"));
            response.setResult(translator.toLocale("s4"));
            log.info("course already exist");
            return response;

        }catch (Exception e){
            log.info("internal server error : {}" , e.getMessage());
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s5"));
            return response;
        }
    }
    public GetCoursesResponse getAll(){
        log.info("finding all courses");
        GetCoursesResponse getCoursesResponse = new GetCoursesResponse();
        List<Course> courseList = courseRepository.findAll();
        List<String> courseStrings = new ArrayList<>();
        for(Course c : courseList){
            courseStrings.add(c.getName()) ;
        }
        getCoursesResponse.setCourses(courseStrings);

        return getCoursesResponse;
    }

}
