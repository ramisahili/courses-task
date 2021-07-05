package com.task.demo.service;

import com.task.demo.entity.Class;
import com.task.demo.entity.ClassDate;
import com.task.demo.entity.Instructor;
import com.task.demo.http.request.AddClassDateRequest;
import com.task.demo.repository.ClassDateRepository;
import com.task.demo.repository.ClassRepository;
import com.task.demo.repository.InstructorRepository;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.shared.helper.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClassDateService {

    private final ClassDateRepository classDateRepository;

    private final ClassRepository classRepository;

    private final Translator translator;


    public AddingResponse add(AddClassDateRequest request){
        log.info("Assigning time to the class");
        AddingResponse response = new AddingResponse();
        try{
            Optional<Class> aClass = classRepository.findById(request.getClassId());
            if(aClass.isPresent()){
                List<Class> instructorClasses = classRepository.findByInstructorId(aClass.get().getInstructor().getId());
                if(instructorClasses.size()==0){
                    ClassDate classDate = new ClassDate();
                    classDate.setCla(aClass.get());
                    classDate.setDay(request.getDay());
                    classDate.setStartTime(request.getStartTime());
                    classDate.setEndTime(request.getEndTime());
                    classDateRepository.save(classDate);
                    log.info("class date added successfully");
                    response.setResult(translator.toLocale("s1"));
                    response.setDescription(translator.toLocale("s6"));
                    return response;
                }
                boolean timeConflict = false ;
                for(Class c : instructorClasses){
                    List<ClassDate> classDates = classDateRepository.findByClaId(c.getId());
                    for(ClassDate date : classDates){
                        if(date.getDay().equals(request.getDay())){
                            if(request.getStartTime().isBefore(date.getStartTime()) && request.getEndTime().isAfter(date.getStartTime())
                            || request.getStartTime().isAfter(date.getStartTime()) && request.getStartTime().isBefore(date.getEndTime())
                            || request.getStartTime().equals(date.getStartTime())){
                                timeConflict = true ;
                                break;
                            }
                        }
                    }
                }
                if(timeConflict){
                    log.error("time conflict , instructor has another class at this time");
                    response.setResult(translator.toLocale("s4"));
                    response.setDescription(translator.toLocale("s7"));
                    return response;
                }
                ClassDate classDate = new ClassDate();
                classDate.setCla(aClass.get());
                classDate.setDay(request.getDay());
                classDate.setStartTime(request.getStartTime());
                classDate.setEndTime(request.getEndTime());
                classDateRepository.save(classDate);

                log.info("class date added successfully");
                response.setResult(translator.toLocale("s1"));
                response.setDescription(translator.toLocale("s6"));
                return response;

                }
            log.error("Class does not exist");
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s8"));
            return response;

        }catch (Exception e){
            log.error("internal server error");
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s5"));
            return response;
        }
    }
}
