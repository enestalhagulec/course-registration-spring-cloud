package net.etg.studentservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.studentservice.dto.CourseDTO;
import net.etg.studentservice.dto.RegistrationDTO;
import net.etg.studentservice.dto.StudentDTO;
import net.etg.studentservice.entity.Student;
import net.etg.studentservice.exception.ErrorDetail;
import net.etg.studentservice.exception.NoCourseException;
import net.etg.studentservice.exception.NoStudentException;
import net.etg.studentservice.proxy.OpenFeignCourseProxy;
import net.etg.studentservice.proxy.OpenFeignRegisterProxy;
import net.etg.studentservice.repository.StudentRepository;
import net.etg.studentservice.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final OpenFeignCourseProxy openFeignCourseProxy;
    private final OpenFeignRegisterProxy openFeignRegisterProxy;

    @Override
    public StudentDTO saveStudent(StudentDTO studentDTO){
        Student student = modelMapper.map(studentDTO,Student.class);
        Student savedStudent = studentRepository.save(student);
        StudentDTO savedStudentDTO = modelMapper.map(savedStudent,StudentDTO.class);
        return savedStudentDTO;
    }

    @Override
    public StudentDTO getStudent(String studentId){
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(NoStudentException::new);
        return modelMapper.map(existingStudent,StudentDTO.class);
    }


    @Override
    public StudentDTO updateStudent(String studentId,
                                    StudentDTO studentDTO){
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(NoStudentException::new);
        existingStudent.setName(studentDTO.getName());
        existingStudent.setLastName(studentDTO.getLastName());
        Student updatedStudent = studentRepository.save(existingStudent);
        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    @Override
    public String deleteStudent(String studentId){
        if(studentRepository.findById(studentId).isPresent()){
            studentRepository.deleteById(studentId);
            return "Student with id: " + studentId + " deleted.";
        }
        throw new NoStudentException();

    }


    @Override
    public RegistrationDTO registerToACourse(RegistrationDTO registrationDTO) {
        return openFeignRegisterProxy.registerToACourse(registrationDTO).getBody();
    }

    @Override
    public List<CourseDTO> bringAllCourses(){
        return openFeignCourseProxy.getAllCourses().getBody();
    }


    @Override
    public List<StudentDTO> getStudents(List<String> studentIds) {

        List<Student> students = new ArrayList<>();
        studentIds.forEach(
                (studentId) -> {
                    Student student = studentRepository.findById(studentId).orElseThrow(NoStudentException::new);
                    students.add(student);
                }
        );
        return students
                .stream()
                .map(student -> modelMapper.map(student,StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getEnrolledCourses(String studentId) {
        if(studentRepository.findById(studentId).isPresent()){
            Object response = openFeignRegisterProxy.getStudentCourses(studentId).getBody();
            if(response instanceof ErrorDetail){
                throw new NoCourseException();
            }
            return (List<CourseDTO>) response;
        }
        throw new NoStudentException();
    }

}
