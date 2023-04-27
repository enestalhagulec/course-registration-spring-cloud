package net.etg.courseservice.service.impl;

import net.etg.courseservice.dto.CourseDTO;
import net.etg.courseservice.entity.Course;
import net.etg.courseservice.repository.CourseRepository;
import net.etg.courseservice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public CourseDTO saveCourse(CourseDTO courseDTO){
        Course course = modelMapper.map(courseDTO, Course.class);
        Course savedCourse = courseRepository.save(course);
        CourseDTO savedCourseDTO = modelMapper.map(savedCourse, CourseDTO.class);
        return savedCourseDTO;
    }


    public CourseDTO getCourse(String courseCode){
        Course existingCourse = courseRepository.findById(courseCode).get();
        CourseDTO existingCourseDTO = modelMapper.map(existingCourse, CourseDTO.class);
        return existingCourseDTO;
    }

    public CourseDTO updateCourse(CourseDTO courseDTO, String courseCode){
        Course existingCourse = courseRepository.findById(courseCode).get();
        existingCourse.setName(courseDTO.getName());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setInstructorId(courseDTO.getInstructorId());
        Course updatedCourse = courseRepository.save(existingCourse);
        return modelMapper.map(updatedCourse, CourseDTO.class);
    }

    public String deleteCourse(String courseCode){
        courseRepository.deleteById(courseCode);
        return "Course with code " + courseCode + " deleted";
    }

    public List<CourseDTO> findAllCourses(){
        List<Course> courses = courseRepository.findAll();
        return courses
                .stream()
                .map(course -> modelMapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());
    }



    @Override
    public List<CourseDTO> getCoursesByInstructorId(String instructorId){
        ArrayList<Course> courses = courseRepository.findCoursesByInstructorId(instructorId).get();
        List<CourseDTO> courseDTOList = courses
                                            .stream()
                                            .map(course -> modelMapper.map(course,CourseDTO.class))
                                            .collect(Collectors.toList());
        return courseDTOList;

    }


    @Override
    public List<CourseDTO> getCoursesByCodes(List<String> courseCodes) {
        System.out.println(courseCodes);
        List<Course> courses = new ArrayList<>();
        for(int i=0; i<courseCodes.size();i++){
            Course existingCourse = courseRepository.findById(courseCodes.get(i)).get();
            courses.add(existingCourse);
        }

        List<CourseDTO> courseDTOList = courses
                                            .stream()
                                            .map(course -> modelMapper.map(course,CourseDTO.class))
                                            .collect(Collectors.toList());

        return courseDTOList;

    }


}
