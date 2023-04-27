package net.etg.instructorservice.controller;

import net.etg.instructorservice.dto.CourseDTO;
import net.etg.instructorservice.dto.InstructorDTO;
import net.etg.instructorservice.dto.StudentDTO;
import net.etg.instructorservice.entity.Instructor;
import net.etg.instructorservice.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping("/add")
    public ResponseEntity<InstructorDTO> saveInstructor(@RequestBody InstructorDTO instructorDTO){
        InstructorDTO savedInstructorDTO = instructorService.saveInstructor(instructorDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedInstructorDTO);
    }

    @GetMapping("/get/{instructorId}")
    public ResponseEntity<InstructorDTO> getInstructor(@PathVariable String instructorId){
        InstructorDTO existingInstructorDTO = instructorService.getInstructor(instructorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(existingInstructorDTO);
    }

    @PutMapping("/update/{instructorId}")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable String instructorId,
                                                          @RequestBody InstructorDTO instructorDTO){
        InstructorDTO updatedInstructorDTO = instructorService.updateInstructor(instructorId,instructorDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedInstructorDTO);
    }

    @DeleteMapping("/delete/{instructorId}")
    public String deleteInstructor(@PathVariable String instructorId){
        return instructorService.deleteInstructor(instructorId);
    }

    @PostMapping("/add/course/")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(instructorService.createACourse(courseDTO));
    }

    @GetMapping("/get/courses/{instructorId}")
    public ResponseEntity<List<CourseDTO>> getInstructorCourses(@PathVariable String instructorId){
        List<CourseDTO> courseDTOList = instructorService.bringInstructorCourses(instructorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseDTOList);
    }

    @GetMapping("/get/{instructorId}/{courseCode}")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourse(@PathVariable String instructorId,
                                                                @PathVariable String courseCode){
        List<StudentDTO> studentDTOList = instructorService.bringEnrolledStudents(instructorId,courseCode);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentDTOList);
    }

}
