package net.etg.studentservice.proxy;

import net.etg.studentservice.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "COURSE-SERVICE")
public interface OpenFeignCourseProxy {

    @GetMapping("course/get/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses();
}
