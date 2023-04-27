package net.etg.courseservice.service;

import net.etg.courseservice.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    CourseDTO saveCourse(CourseDTO courseDTO);
    CourseDTO getCourse(String courseCode);
    CourseDTO updateCourse(CourseDTO courseDTO, String courseCode);
    String deleteCourse(String courseCode);
    List<CourseDTO> getCoursesByInstructorId(String instructorId);
    List<CourseDTO> findAllCourses();

    List<CourseDTO> getCoursesByCodes(List<String> courseCodes);
}
