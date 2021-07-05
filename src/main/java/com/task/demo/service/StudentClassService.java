package com.task.demo.service;

import com.task.demo.entity.*;
import com.task.demo.entity.Class;
import com.task.demo.http.request.AddStudentClassRequest;
import com.task.demo.http.response.*;
import com.task.demo.repository.*;
import com.task.demo.shared.dto.AddingResponse;
import com.task.demo.shared.helper.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class StudentClassService {

    private final StudentClassRepository studentClassRepository ;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository ;
    private final ClassDateRepository classDateRepository;
    private final PrerequisiteRepository prerequisiteRepository ;
    private final CourseHistoryRepository courseHistoryRepository;
    private final CourseRepository courseRepository ;

    private final Translator translator;

    @Value("${course.max-num-credits: #{null}}")
    private String max_number_of_Credits;

    public AddingResponse add(AddStudentClassRequest request){
        log.info("enrolling class ");
        AddingResponse response = new AddingResponse();
        try
        {

            Optional<Student> student = studentRepository.findById(request.getStudentId());
            Optional<Class> classToEnroll = classRepository.findById(request.getClassId());

                if(student.isPresent() && classToEnroll.isPresent()){
                    if(this.getNumberOfCredits(student.get()) < Integer.valueOf(max_number_of_Credits)){
                    List<StudentClass> studentEnrolledClasses = studentClassRepository.findByStudentId(student.get().getId());

                    log.info("course {}" , classToEnroll.get().getCourse().getName());
                    List<Prerequisite> coursePrerequisite = prerequisiteRepository.findByParentId(classToEnroll.get().getCourse().getId());
                    if(coursePrerequisite.size() == 0){

                        if(studentEnrolledClasses.size() == 0){
                            StudentClass studentClass = new StudentClass();
                            studentClass.setStudent(student.get());
                            studentClass.setCla(classToEnroll.get());
                            studentClassRepository.save(studentClass);
                            log.info("class has been enrolled successfully for student {}",student.get().getFirstName() + student.get().getLastName());
                            response.setResult(translator.toLocale("s1"));
                            response.setDescription(translator.toLocale("s12"));
                            return response;
                        }

                        log.info("student have classes in his schedule");
                        return checkCourse(response , classToEnroll , studentEnrolledClasses ,student) ;

                    }
                    log.info("course have prerequisite");
                    List<CourseHistory> studentCourseHistory = courseHistoryRepository.findByStudentId(student.get().getId());
                    boolean validate = false ;
                    List<String> validatorList = new ArrayList<>();
                    for(Prerequisite p : coursePrerequisite){
                        for(CourseHistory h : studentCourseHistory){
                            if(p.getChild().getId() == h.getCourse().getId())
                                validatorList.add(p.getChild().getName());
                        }
                    }
                    if(validatorList.size() == coursePrerequisite.size()){
                        log.info("student validate this course and can enrol it ");
                        return checkCourse(response , classToEnroll , studentEnrolledClasses ,student) ;
                    }
                    log.error("student can't add this course because does not validate the prerequisite");
                    response.setResult(translator.toLocale("s4"));
                    response.setDescription(translator.toLocale("s17"));
                    return response ;
                }
                log.error("you have reached the maximum number of credits");
                response.setResult(translator.toLocale("s4"));
                response.setDescription(translator.toLocale("s19"));
                return response ;
            }

            log.error("student or class does not exist");
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s20"));
            return response;
        }catch (Exception e){
                log.error("internal server error");
                response.setResult(translator.toLocale("s4"));
                response.setDescription(translator.toLocale("s5"));
                return response;
        }
    }

    public GetStudentsDetailsResponse getAll(){
        GetStudentsDetailsResponse response = new GetStudentsDetailsResponse();
        List<StudentInfo> studentInfoList = new ArrayList<>();
        List<Student> allStudents = studentRepository.findAll();
        for(Student d : allStudents){
            List<ClassDetails> classDetailsList = new ArrayList<>();
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setFirstname(d.getFirstName());
            studentInfo.setLastname(d.getLastName());
            List<StudentClass> classes = studentClassRepository.findByStudentId(d.getId());
            if(classes.size()>0){
                for (StudentClass studentClass : classes){
                    List<ClassDates> classDates = new ArrayList<>();
                    Optional<Class> aClass = classRepository.findById(studentClass.getCla().getId());
                    if(aClass.isPresent()){
                        Optional<Course> course = courseRepository.findById(aClass.get().getCourse().getId());
                        ClassDetails classDetails = new ClassDetails();
                        classDetails.setClassName(course.get().getName());
                        List<ClassDate> classDates1 = classDateRepository.findByClaId(aClass.get().getId());
                        for(ClassDate classDate : classDates1){
                            ClassDates classDate1 = new ClassDates();
                            classDate1.setDay(classDate.getDay());
                            classDate1.setStartTime(classDate.getStartTime());
                            classDate1.setEndTime(classDate.getEndTime());
                            classDates.add(classDate1) ;
                        }
                        classDetails.setClassDatesList(classDates);
                        classDetailsList.add(classDetails);
                    }
                }
                studentInfo.setClassDetailsList(classDetailsList);
            }
            studentInfoList.add(studentInfo);


        }
        response.setStudentInfoList(studentInfoList);
        return response;
    }


    private AddingResponse checkCourse(AddingResponse response , Optional<Class> classToEnroll , List<StudentClass> studentEnrolledClasses , Optional<Student> student ){
        List<ClassDate> classToEnrollDates = classDateRepository.findByClaId(classToEnroll.get().getId());
        boolean timeConflict = false;
        for(StudentClass studentClass : studentEnrolledClasses){
            List<ClassDate> dates = classDateRepository.findByClaId(studentClass.getCla().getId());
            for(ClassDate date : dates){
                for(ClassDate toEnrollDate : classToEnrollDates){
                    if(date.getDay().equals(toEnrollDate.getDay())){
                        if(toEnrollDate.getStartTime().isBefore(date.getEndTime()) && toEnrollDate.getEndTime().isAfter(date.getStartTime())
                                || toEnrollDate.getStartTime().isAfter(date.getStartTime()) && toEnrollDate.getStartTime().isBefore(date.getEndTime())
                                ||toEnrollDate.getStartTime().equals(date.getStartTime())){
                            log.info("time conflict");
                            timeConflict = true;
                            break;
                        }
                    }
                }
            }
        }
        if(timeConflict){
            log.error("time conflict , can't enroll this class");
            response.setResult(translator.toLocale("s4"));
            response.setDescription(translator.toLocale("s18"));
            return response ;
        }

        StudentClass studentClass = new StudentClass();
        studentClass.setStudent(student.get());
        studentClass.setCla(classToEnroll.get());
        studentClassRepository.save(studentClass);
        log.info("class has been enrolled successfully for student {}",student.get().getFirstName() + student.get().getLastName());
        response.setResult(translator.toLocale("s1"));
        response.setDescription(translator.toLocale("s12"));
        return response;
    }

    private int getNumberOfCredits(Student student){
        List<StudentClass> classes = studentClassRepository.findByStudentId(student.getId());
        int numberOfCredits = 0;
        for (StudentClass studentClass : classes){
            numberOfCredits +=studentClass.getCla().getCourse().getCredits();
        }
        return numberOfCredits;
    }

}
