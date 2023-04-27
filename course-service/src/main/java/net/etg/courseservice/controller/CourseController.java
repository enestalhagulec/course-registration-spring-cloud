package net.etg.courseservice.controller;

import lombok.AllArgsConstructor;
import net.etg.courseservice.dto.CourseDTO;
import net.etg.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;


    @GetMapping("/get/{courseCode}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable String courseCode){
        CourseDTO existingCourseDTO = courseService.getCourse(courseCode);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(existingCourseDTO);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        List<CourseDTO> courseDTOList = courseService.findAllCourses();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseDTOList);
    }

    @PostMapping("/add")
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO){
        CourseDTO savedCourseDTO = courseService.saveCourse(courseDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCourseDTO);
    }

    @PutMapping("/update/{courseCode}")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO,
                                                  @PathVariable String courseCode){
        CourseDTO updatedCourseDTO = courseService.updateCourse(courseDTO,courseCode);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCourseDTO);
    }

    @GetMapping("/get/instructor-courses/{instructorId}")
    public ResponseEntity<List<CourseDTO>> getInstructorCourses(@PathVariable String instructorId){
        List<CourseDTO> courseDTOList = courseService.getCoursesByInstructorId(instructorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseDTOList);
    }

    //this must be GetMapping but OpenFeign converts get request to post request automatically
    @PostMapping("/get/s-courses")
    public ResponseEntity<List<CourseDTO>> getCourses(@RequestBody List<String> courseCodes){
        List<CourseDTO> courseDTOList = courseService.getCoursesByCodes(courseCodes);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseDTOList);
    }


}
