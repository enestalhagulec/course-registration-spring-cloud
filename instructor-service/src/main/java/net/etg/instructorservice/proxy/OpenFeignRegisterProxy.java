package net.etg.instructorservice.proxy;

import net.etg.instructorservice.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "REGISTER-SERVICE")
public interface OpenFeignRegisterProxy {

    @GetMapping("/get/instructor-course/{courseId}")
    ResponseEntity<List<StudentDTO>> getStudents(@PathVariable String courseId);
}
