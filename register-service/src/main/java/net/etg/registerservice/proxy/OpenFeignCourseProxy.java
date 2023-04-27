package net.etg.registerservice.proxy;

import net.etg.registerservice.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "COURSE-SERVICE")
public interface OpenFeignCourseProxy {

    //this must be GetMapping, but OpenFeign changes request automatically to the post
    @PostMapping("/course/get/s-courses")
    ResponseEntity<List<CourseDTO>> getCourses(@RequestBody List<String> courseCodes);

}
