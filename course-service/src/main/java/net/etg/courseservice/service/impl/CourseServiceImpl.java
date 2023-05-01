package net.etg.courseservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.courseservice.dto.CourseDTO;
import net.etg.courseservice.entity.Course;
import net.etg.courseservice.exception.NoCourseException;
import net.etg.courseservice.exception.NoInstructorException;
import net.etg.courseservice.repository.CourseRepository;
import net.etg.courseservice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public CourseDTO saveCourse(CourseDTO courseDTO){
        Course course = modelMapper.map(courseDTO, Course.class);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDTO.class);

    }


    public CourseDTO getCourse(String courseCode){
        Course existingCourse = courseRepository.findById(courseCode).orElseThrow(NoCourseException::new);
        return modelMapper.map(existingCourse, CourseDTO.class);

    }

    public CourseDTO updateCourse(CourseDTO courseDTO, String courseCode){
        Course existingCourse = courseRepository.findById(courseCode).orElseThrow(NoInstructorException::new);
        existingCourse.setName(courseDTO.getName());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setInstructorId(courseDTO.getInstructorId());
        Course updatedCourse = courseRepository.save(existingCourse);
        return modelMapper.map(updatedCourse, CourseDTO.class);
    }

    public String deleteCourse(String courseCode){
        Optional<Course> optionalCourse = courseRepository.findById(courseCode);
        if(optionalCourse.isPresent()){
            courseRepository.deleteById(courseCode);
            return "Course with code " + courseCode + " deleted";
        }else{
            throw new NoCourseException();
        }

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
        ArrayList<Course> courses = courseRepository.findCoursesByInstructorId(instructorId).orElseThrow(NoInstructorException::new);
        return courses
                .stream()
                .map(course -> modelMapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<CourseDTO> getCoursesByCodes(List<String> courseCodes) {
        List<Course> courses = new ArrayList<>();
        courseCodes.forEach(
                courseCode -> {
                    Course course = courseRepository.findById(courseCode).orElseThrow(NoCourseException::new);
                    courses.add(course);
                }
        );
        return courses
                .stream()
                .map(course -> modelMapper.map(course,CourseDTO.class))
                .collect(Collectors.toList());


    }


}
