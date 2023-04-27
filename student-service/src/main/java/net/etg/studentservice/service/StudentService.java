package net.etg.studentservice.service;

import net.etg.studentservice.dto.CourseDTO;
import net.etg.studentservice.dto.RegistrationDTO;
import net.etg.studentservice.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO saveStudent(StudentDTO studentDTO);

    StudentDTO getStudent(String studentId);

    public List<CourseDTO> bringAllCourses();

    StudentDTO updateStudent(String studentId, StudentDTO studentDTO);

    String deleteStudent(String studentId);

    RegistrationDTO registerToACourse(RegistrationDTO registrationDTO);

    List<StudentDTO> getStudents(List<String> studentId);

    List<CourseDTO> getEnrolledCourses(String studentId);

}
