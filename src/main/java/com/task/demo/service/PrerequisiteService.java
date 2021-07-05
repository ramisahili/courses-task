package com.task.demo.service;

import com.task.demo.entity.Course;
import com.task.demo.entity.Prerequisite;
import com.task.demo.http.request.AddPrerequisiteRequest;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.repository.CourseRepository;
import com.task.demo.repository.PrerequisiteRepository;
import com.task.demo.shared.helper.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrerequisiteService {


    private final PrerequisiteRepository prerequisiteRepository ;

    private final CourseRepository courseRepository ;
    private final Translator translator;


    public AddingResponse addPrerequisite(AddPrerequisiteRequest request){
        log.info("adding prerequisite ....");
        AddingResponse response = new AddingResponse();

        try{
            Optional<Course> course = courseRepository.findById(request.getCourseId());
            Optional<Course> prerequisite = courseRepository.findById(request.getPrerequisiteId());

            if( request.getCourseId() != request.getPrerequisiteId()){
                if(course.isPresent() && prerequisite.isPresent()){
                    List<Prerequisite> prerequisites = prerequisiteRepository.findByParentId(request.getCourseId());
                    Prerequisite prerequisiteToAdd = new Prerequisite();
                    prerequisiteToAdd.setParent(course.get());
                    prerequisiteToAdd.setChild(prerequisite.get());
                    prerequisiteRepository.save(prerequisiteToAdd);
                    if(prerequisites.size() == 0){

                        log.info("prerequisite is assigned successfully to the course");
                        response.setDescription(translator.toLocale("s14"));
                        response.setResult(translator.toLocale("s1"));
                        return response;
                    }
                    for(Prerequisite p : prerequisites){
                        if(p.getChild().getId() == request.getPrerequisiteId()){
                            log.info("this course already have this prerequisite");
                            response.setDescription(translator.toLocale("s15"));
                            response.setResult(translator.toLocale("s4"));
                            return response;
                        }
                    }
                    prerequisiteRepository.save(prerequisiteToAdd);
                    log.info("prerequisite is assigned successfully to the course");
                    response.setDescription(translator.toLocale("s14"));
                    response.setResult(translator.toLocale("s1"));
                    return response;
                }
                response.setResult(translator.toLocale("s4"));
                response.setDescription("course or prerequisite does not exist");
                return response ;
            }
            response.setResult("Error");
            response.setDescription("can't add the same course as prerequisite");
            return response ;
        }catch (Exception e){
            log.info("internal server error");
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s5"));
            return  response;
        }
    }
}
