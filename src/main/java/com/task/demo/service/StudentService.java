package com.task.demo.service;

import com.task.demo.entity.Student;
import com.task.demo.http.request.AddStudentRequest;
import com.task.demo.http.response.GetStudentResponse;
import com.task.demo.repository.StudentRepository;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.shared.dto.StudentDto;
import com.task.demo.shared.helper.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository ;

    private final Translator translator;

    public AddingResponse add(AddStudentRequest request){
        log.info("Adding student {} " ,request.getFirstname() + " " +request.getLastname()) ;
        AddingResponse response = new AddingResponse();
        try {
            Student student = new Student();
            student.setFirstName(request.getFirstname());
            student.setLastName(request.getLastname());
            studentRepository.save(student);
            log.info("Student added successfully" ,request) ;
            response.setDescription(translator.toLocale("s16"));
            response.setResult(translator.toLocale("s1"));
            return response;
        }catch (Exception e){
            log.error("Error adding student {}" ,request);
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s5"));
            return response;
        }
    }
    public GetStudentResponse getAll(){
        log.info("finding students");
        GetStudentResponse getStudentResponse = new GetStudentResponse();
        List<StudentDto> studentsDto = new ArrayList<>();
        List<Student> students = studentRepository.findAll();
        for(Student s : students){
            StudentDto studentDto = new StudentDto();
            studentDto.setFirstname(s.getFirstName());
            studentDto.setLastname(s.getLastName());
            studentsDto.add(studentDto);
        }
        getStudentResponse.setStudents(studentsDto);
        return getStudentResponse;
    }


}
