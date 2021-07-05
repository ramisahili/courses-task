package com.task.demo.service;

import com.task.demo.entity.Class;
import com.task.demo.entity.Course;
import com.task.demo.entity.Instructor;
import com.task.demo.http.request.AddClassRequest;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.repository.ClassRepository;
import com.task.demo.repository.CourseRepository;
import com.task.demo.repository.InstructorRepository;
import com.task.demo.shared.helper.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClassService {


    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    private final Translator translator;

    public AddingResponse addClass(AddClassRequest request){
        log.info("creating class");
        AddingResponse response = new AddingResponse();
        try
        {
            List<Class> instructorClasses= classRepository.findByInstructorId(request.getInstructorId());
            Optional<Course> course = courseRepository.findById(request.getCourseId());
            Optional<Instructor> instructor = instructorRepository.findById(request.getInstructorId());

            if(course.isPresent()==true && instructor.isPresent()==true){
                Class classToBeCreated = new Class();
                classToBeCreated.setCourse(course.get());
                classToBeCreated.setInstructor(instructor.get());

                if(instructorClasses.size()==0){
                    classRepository.save(classToBeCreated);
                    log.info("Class created successfully");
                    response.setResult(translator.toLocale("s1"));
                    response.setDescription(translator.toLocale("s10"));
                    return response;
                    }

                boolean exist = false ;
                for(Class s : instructorClasses){
                    if(s.getCourse().getId() == course.get().getId()){
                        exist = true;
                    }
                }
                if(exist == true)
                {
                    response.setResult(translator.toLocale("s4"));
                    response.setDescription(translator.toLocale("s11"));
                    return response;
                }
                classRepository.save(classToBeCreated);
                log.info("Class created successfully");
                response.setResult(translator.toLocale("s1"));
                response.setDescription(translator.toLocale("s10"));
                return response;
            }
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s9"));
            return response;

        }catch (Exception e){
            log.info("internal error :{}",e.getMessage());
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s5"));
            return response;
        }
    }
}
