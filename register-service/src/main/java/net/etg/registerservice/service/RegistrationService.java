package net.etg.registerservice.service;

import net.etg.registerservice.dto.CourseDTO;
import net.etg.registerservice.dto.RegistrationDTO;
import net.etg.registerservice.dto.StudentDTO;

import java.util.List;

public interface RegistrationService {

    RegistrationDTO register(RegistrationDTO registrationDTO);

    List<CourseDTO> getCoursesByStudentId(String studentId);

    List<StudentDTO> getStudentsByCourse(String courseCode);

}
