package net.etg.instructorservice.proxy;

import net.etg.instructorservice.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "COURSE-SERVICE")
public interface OpenFeignCourseProxy {

    @PostMapping("course/add")
    ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO);


    @GetMapping("course/get/instructor-courses/{instructorId}")
    ResponseEntity<List<CourseDTO>> getInstructorCourses(@PathVariable String instructorId);


}
