package net.etg.instructorservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.instructorservice.dto.CourseDTO;
import net.etg.instructorservice.dto.InstructorDTO;
import net.etg.instructorservice.dto.StudentDTO;
import net.etg.instructorservice.entity.Instructor;
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
        InstructorDTO savedInstructDTO = modelMapper.map(savedInstructor, InstructorDTO.class);
        return savedInstructDTO;
    }


    public InstructorDTO getInstructor(String instructorId){
        Instructor existingInstructor = instructorRepository.findById(instructorId).get();
        InstructorDTO existingInstructorDTO = modelMapper.map(existingInstructor,InstructorDTO.class);
        return existingInstructorDTO;
    }

    public InstructorDTO updateInstructor(String instructorId,InstructorDTO instructorDTO){
        Instructor existingInstructor = instructorRepository.findById(instructorId).get();
        existingInstructor.setName(instructorDTO.getName());
        existingInstructor.setLastName(instructorDTO.getLastName());
        Instructor updatedInstructor = instructorRepository.save(existingInstructor);
        InstructorDTO updatedInstructorDTO = modelMapper.map(updatedInstructor,InstructorDTO.class);
        return updatedInstructorDTO;
    }

    public String deleteInstructor(String instructorId){
        instructorRepository.deleteById(instructorId);
        return "Instructor with code " + instructorId + " deleted";
    }


    public CourseDTO createACourse(CourseDTO courseDTO){
        Instructor instructor = instructorRepository.findById(courseDTO.getInstructorId()).get();
        instructor.addCourse(courseDTO.getCourseCode());
        instructorRepository.save(instructor);
        CourseDTO savedCourseDTO = openFeignCourseProxy.saveCourse(courseDTO).getBody();
        return savedCourseDTO;
    }


    public List<CourseDTO> bringInstructorCourses(String instructorId){
        return openFeignCourseProxy.getInstructorCourses(instructorId).getBody();
    }

    @Override
    public List<StudentDTO> bringEnrolledStudents(String instructorId, String courseCode) {
        List<StudentDTO> studentDTOList = openFeignRegisterProxy.getStudents(courseCode).getBody();
        return studentDTOList;
    }

}
