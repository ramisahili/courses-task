package com.task.demo.service;

import com.task.demo.entity.*;
import com.task.demo.entity.Class;
import com.task.demo.http.request.AddInstructorRequest;
import com.task.demo.http.response.*;
import com.task.demo.repository.ClassDateRepository;
import com.task.demo.repository.ClassRepository;
import com.task.demo.repository.InstructorRepository;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.shared.helper.Translator;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository ;
    private final ClassRepository classRepository;
    private final ClassDateRepository classDateRepository ;
    private final Translator translator;

    public AddingResponse add(AddInstructorRequest request){

        log.info("adding instructor {}",request.getInstructorName());
        AddingResponse response = new AddingResponse();
        try{
            Instructor instructor = new Instructor();
            instructor.setName(request.getInstructorName());
            instructorRepository.save(instructor);
            response.setDescription(translator.toLocale("s12"));
            response.setResult(translator.toLocale("s1"));
            log.info("Instructor added successfully");
            return  response;
        }catch (Exception e){
            log.info("error adding {} " , e.getMessage());
            response.setDescription(translator.toLocale("s5"));
            response.setResult(translator.toLocale("s4"));
            return response;
        }
    }

    public GetInstructorsResponse getAll(){
        log.info("finding all instructors");
        GetInstructorsResponse response = new GetInstructorsResponse();
        List<InstructorInfo> instructorInfoList = new ArrayList<>();
        List<Instructor> allInstructors = instructorRepository.findAll();
        for(Instructor d : allInstructors){
            List<ClassDetails> classDetailsList = new ArrayList<>();
            InstructorInfo instructorInfo = new InstructorInfo();
            instructorInfo.setName(d.getName());
            List<Class> classes = classRepository.findByInstructorId(d.getId());
            for(Class aClass : classes){
                List<ClassDates> classDatesList = new ArrayList<>();
                ClassDetails classDetails = new ClassDetails();
                classDetails.setClassName(aClass.getCourse().getName());
                List<ClassDate> classDates = classDateRepository.findByClaId(aClass.getId());
                for (ClassDate classDate : classDates){
                    ClassDates classDates1 = new ClassDates();
                    classDates1.setDay(classDate.getDay());
                    classDates1.setEndTime(classDate.getEndTime());
                    classDates1.setStartTime(classDate.getStartTime());
                    classDatesList.add(classDates1);
                }
                classDetails.setClassDatesList(classDatesList);
                classDetailsList.add(classDetails);
            }
            instructorInfoList.add(instructorInfo);
            instructorInfo.setClassDetailsList(classDetailsList);
        }

        response.setInstructorInfoList(instructorInfoList);
        return response;
    }


}
