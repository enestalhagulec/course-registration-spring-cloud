package net.etg.instructorservice.service;

import net.etg.instructorservice.dto.CourseDTO;
import net.etg.instructorservice.dto.InstructorDTO;
import net.etg.instructorservice.dto.StudentDTO;

import java.util.List;

public interface InstructorService {

    InstructorDTO saveInstructor(InstructorDTO instructorDTO);

    InstructorDTO getInstructor(String instructorId);

    InstructorDTO updateInstructor(String instructorId,InstructorDTO instructorDTO);

    String deleteInstructor(String instructorId);

    CourseDTO createACourse(CourseDTO courseDTO);

    List<CourseDTO> bringInstructorCourses(String instructorId);

    List<StudentDTO> bringEnrolledStudents(String instructorId, String courseCode);
}
