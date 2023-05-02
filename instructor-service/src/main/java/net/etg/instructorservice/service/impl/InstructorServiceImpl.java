package net.etg.instructorservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.instructorservice.dto.CourseDTO;
import net.etg.instructorservice.dto.InstructorDTO;
import net.etg.instructorservice.dto.StudentDTO;
import net.etg.instructorservice.entity.Instructor;
import net.etg.instructorservice.exception.*;
import net.etg.instructorservice.proxy.OpenFeignCourseProxy;
import net.etg.instructorservice.proxy.OpenFeignRegisterProxy;
import net.etg.instructorservice.repository.InstructorRepository;
import net.etg.instructorservice.service.InstructorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final ModelMapper modelMapper;
    private final OpenFeignCourseProxy openFeignCourseProxy;
    private final OpenFeignRegisterProxy openFeignRegisterProxy;



    public InstructorDTO saveInstructor(InstructorDTO instructorDTO){
        Instructor instructor = modelMapper.map(instructorDTO,Instructor.class);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return modelMapper.map(savedInstructor, InstructorDTO.class);
    }


    public InstructorDTO getInstructor(String instructorId){
        Instructor existingInstructor = instructorRepository.findById(instructorId).orElseThrow(NoInstructorException::new);
        return modelMapper.map(existingInstructor,InstructorDTO.class);
    }

    public InstructorDTO updateInstructor(String instructorId,InstructorDTO instructorDTO){
        Instructor existingInstructor = instructorRepository.findById(instructorId).orElseThrow(NoInstructorException::new);
        existingInstructor.setName(instructorDTO.getName());
        existingInstructor.setLastName(instructorDTO.getLastName());
        Instructor updatedInstructor = instructorRepository.save(existingInstructor);
        return modelMapper.map(updatedInstructor,InstructorDTO.class);
    }

    public String deleteInstructor(String instructorId){
        if(instructorRepository.findById(instructorId).isPresent()){
            instructorRepository.deleteById(instructorId);
            return "Instructor with code " + instructorId + " deleted";
        }
        throw new NoInstructorException();
    }


    // I need to look if instructor already has the course or not, so before adding, i need to select from db
    //if it is present, check, not present go add
    public CourseDTO createCourse(CourseDTO courseDTO){
        Instructor instructor = instructorRepository.findById(courseDTO.getInstructorId()).orElseThrow(NoInstructorException::new);
        Optional<List<String>> optionalCourseCodes = instructorRepository.getCourseCodesForAnInstructor(courseDTO.getInstructorId());
        if(optionalCourseCodes.isPresent()){
            List<String> courseCodes = optionalCourseCodes.get();
            courseCodes.forEach(
                    (courseCode) -> {
                        if(courseCode == courseDTO.getCourseCode())
                            throw new CourseAlreadyCreatedException();
                    }
            );
        }
        instructor.addCourse(courseDTO.getCourseCode());
        instructorRepository.save(instructor);
        return openFeignCourseProxy.createCourse(courseDTO).getBody();
    }


    // 2 scenarios are possible
    //1. instructor might not exist, in this case throw no instructor// check instructor database if exists, convey the request
    // 2. instructor exist but any course doesn't exist for that instructor // throw noCourseException
    public List<CourseDTO> bringInstructorCourses(String instructorId){
        if(instructorRepository.findById(instructorId).isPresent()){
            List<CourseDTO> responseFromCourseService = openFeignCourseProxy.getInstructorCourses(instructorId).getBody();
            if(responseFromCourseService.size() == 0){
                throw new NoCourseException();
            }
            return responseFromCourseService;
        }
        throw new NoInstructorException();

    }

    //2 scenarios possible
    //1. course might not exist, in this case throw course doesn't exist
    // how will we decide if the course exist or not,
    // to decide this, i need to check the response object, if it is instance of CourseDTO, then i can assume course exist,
    // if it is not an object of courseDTO, then i can throw course doesn't exist exception




    //2. course exists but 0 student enrolled to the course(response comes with 0 length list), so throw noStudentException
    @Override
    public List<StudentDTO> bringEnrolledStudents(String courseCode) {
        System.out.println("\n\n\nLETS SEE THE RESPONSE\n\n\n");
        System.out.println(openFeignCourseProxy.getCourse(courseCode).getBody());
        Object responseFromCourseService = openFeignCourseProxy.getCourse(courseCode).getBody();

        System.out.println("\n\n\n\n" + responseFromCourseService + "\n\n\n\n");
        if(responseFromCourseService instanceof CourseDTO){
            Object responseFromRegisterProxy = openFeignRegisterProxy.getStudents(courseCode).getBody();
            if(responseFromRegisterProxy instanceof ErrorDetail){
                throw new NoStudentException();
            }
            return (List<StudentDTO>) responseFromRegisterProxy;
        }
        throw new NoCourseException();

    }

}
