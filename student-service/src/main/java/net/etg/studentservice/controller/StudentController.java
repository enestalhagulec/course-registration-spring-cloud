package net.etg.studentservice.controller;

import net.etg.studentservice.dto.CourseDTO;
import net.etg.studentservice.dto.RegistrationDTO;
import net.etg.studentservice.dto.StudentDTO;
import net.etg.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {


    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO){
        StudentDTO savedStudentDTO = studentService.saveStudent(studentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudentDTO);
    }

    @GetMapping("/get/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String studentId){
        StudentDTO existingStudentDTO = studentService.getStudent(studentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(existingStudentDTO);
    }

    @PutMapping("update/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable String studentId,
                                    @RequestBody StudentDTO studentDTO){
        StudentDTO updatedStudentDTO = studentService.updateStudent(studentId,studentDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedStudentDTO);
    }

    @DeleteMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable String studentId){
        String message = studentService.deleteStudent(studentId);
        return message;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationDTO> registerToACourse(@RequestBody RegistrationDTO registrationDTO){
        RegistrationDTO savedRegistrationDTO = studentService.registerToACourse(registrationDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRegistrationDTO);
    }

    //available courses, in this case, all courses for every student
    @GetMapping("/get/all-courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        List<CourseDTO> courseDTOList = studentService.bringAllCourses();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseDTOList);
    }

    @GetMapping("/get/courses/{studentId}")
    public ResponseEntity<List<CourseDTO>> getEnrolledCourses(@PathVariable String studentId){
        List<CourseDTO> courseDTOList = studentService.getEnrolledCourses(studentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseDTOList);
    }


    //should be GetMapping
    @PostMapping("/get-students")
    public ResponseEntity<List<StudentDTO>> getStudents(@RequestBody List<String> studentIds){
        List<StudentDTO> studentDTOList = studentService.getStudents(studentIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentDTOList);
    }


}
