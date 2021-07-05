package com.task.demo.service;

import com.task.demo.entity.Course;
import com.task.demo.entity.CourseHistory;
import com.task.demo.entity.Student;
import com.task.demo.http.request.AddCourseHistoryRequest;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.repository.CourseHistoryRepository;
import com.task.demo.repository.CourseRepository;
import com.task.demo.repository.StudentRepository;
import com.task.demo.shared.helper.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseHistoryService {

    private final CourseHistoryRepository courseHistoryRepository ;
    private final StudentRepository studentRepository ;
    private final CourseRepository courseRepository ;

    private final Translator translator;

    public AddingResponse add(AddCourseHistoryRequest request){
        log.info("adding course history ");
        AddingResponse response = new AddingResponse() ;
        try{
            Optional<Student> student = studentRepository.findById(request.getStudentId());
            Optional<Course> course = courseRepository.findById(request.getCourseId());
            if(student.isPresent() && course.isPresent()){
                CourseHistory courseHistory = new CourseHistory();
                courseHistory.setCourse(course.get());
                courseHistory.setStudent(student.get());
                courseHistory.setGrade(request.getGrade());
                log.info("course history added successfully");
                courseHistoryRepository.save(courseHistory);
                response.setResult(translator.toLocale("s1"));
                response.setDescription(translator.toLocale("s12"));
                return response;
            }
            log.error("course or student does not exist");
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s13"));
            return response;

        }catch (Exception e){
            log.error("internal server error {}" , e.getMessage());
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s5"));
            return response;
        }
    }
}
