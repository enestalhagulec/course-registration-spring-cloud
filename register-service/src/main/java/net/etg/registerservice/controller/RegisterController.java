package net.etg.registerservice.controller;

import lombok.AllArgsConstructor;
import net.etg.registerservice.dto.CourseDTO;
import net.etg.registerservice.dto.RegistrationDTO;
import net.etg.registerservice.dto.StudentDTO;
import net.etg.registerservice.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationDTO> registerToACourse(@RequestBody RegistrationDTO registrationDTO){

        RegistrationDTO savedRegistrationDTO = registrationService.register(registrationDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRegistrationDTO);
    }

    @GetMapping("/get/courses/{studentId}")
    public ResponseEntity<List<CourseDTO>> getSpecificCourses(@PathVariable String studentId){
        List<CourseDTO> courseDTOList = registrationService.getCoursesByStudentId(studentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseDTOList);
    }

    @GetMapping("/get/instructor-course/{courseId}")
    public ResponseEntity<List<StudentDTO>> getStudents(@PathVariable String courseId){
        List<StudentDTO> studentDTOList = registrationService.getStudentsByCourse(courseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentDTOList);
    }
}
