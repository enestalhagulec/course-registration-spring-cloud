package net.etg.instructorservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.instructorservice.dto.CourseDTO;
import net.etg.instructorservice.dto.InstructorDTO;
import net.etg.instructorservice.dto.StudentDTO;
import net.etg.instructorservice.entity.Instructor;
import net.etg.instructorservice.exception.NoInstructorException;
import net.etg.instructorservice.proxy.OpenFeignCourseProxy;
import net.etg.instructorservice.proxy.OpenFeignRegisterProxy;
import net.etg.instructorservice.repository.InstructorRepository;
import net.etg.instructorservice.service.InstructorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        }else{
            throw new NoInstructorException();
        }
    }


    public CourseDTO createACourse(CourseDTO courseDTO){
        Instructor instructor = instructorRepository.findById(courseDTO.getInstructorId()).orElseThrow(NoInstructorException::new);
        instructor.addCourse(courseDTO.getCourseCode());
        instructorRepository.save(instructor);
        return openFeignCourseProxy.saveCourse(courseDTO).getBody();
    }


    public List<CourseDTO> bringInstructorCourses(String instructorId){
        return openFeignCourseProxy.getInstructorCourses(instructorId).getBody();
    }

    @Override
    public List<StudentDTO> bringEnrolledStudents(String instructorId, String courseCode) {
        return openFeignRegisterProxy.getStudents(courseCode).getBody();
    }

}
