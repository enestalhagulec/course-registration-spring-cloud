package net.etg.studentservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.studentservice.dto.CourseDTO;
import net.etg.studentservice.dto.RegistrationDTO;
import net.etg.studentservice.dto.StudentDTO;
import net.etg.studentservice.entity.Student;
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
        Student existingStudent = studentRepository.findById(studentId).get();
        return modelMapper.map(existingStudent,StudentDTO.class);
    }


    @Override
    public StudentDTO updateStudent(String studentId,
                                    StudentDTO studentDTO){
        Student existingStudent = studentRepository.findById(studentId).get();
        existingStudent.setName(studentDTO.getName());
        existingStudent.setLastName(studentDTO.getLastName());
        Student updatedStudent = studentRepository.save(existingStudent);
        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    @Override
    public String deleteStudent(String studentId){
        studentRepository.deleteById(studentId);
        return "Student with id: " + studentId + " deleted.";
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
        for(int i=0; i< studentIds.size(); i++){
            students.add(studentRepository.findById(studentIds.get(i)).get());
        }
        List<StudentDTO> studentDTOList = students
                                            .stream()
                                            .map(student -> modelMapper.map(student,StudentDTO.class))
                                            .collect(Collectors.toList());

        return studentDTOList;
    }

    @Override
    public List<CourseDTO> getEnrolledCourses(String studentId) {
        List<CourseDTO> enrolledCourseDTOList = openFeignRegisterProxy.getSpecificCourses(studentId).getBody();
        return enrolledCourseDTOList;
    }




}
