package net.etg.studentservice.proxy;

import net.etg.studentservice.dto.CourseDTO;
import net.etg.studentservice.dto.RegistrationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "REGISTER-SERVICE")
public interface OpenFeignRegisterProxy {

    @PostMapping("/register")
    ResponseEntity<RegistrationDTO> registerToACourse(@RequestBody RegistrationDTO registrationDTO);

    @GetMapping("/get/courses/{studentId}")
    ResponseEntity<List<CourseDTO>> getStudentCourses(@PathVariable String studentId);


}
