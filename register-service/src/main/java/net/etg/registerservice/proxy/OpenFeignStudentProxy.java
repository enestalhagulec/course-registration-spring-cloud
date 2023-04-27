package net.etg.registerservice.proxy;

import net.etg.registerservice.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
public interface OpenFeignStudentProxy {

    @PostMapping("/student/get-students")
    ResponseEntity<List<StudentDTO>> getStudents(@RequestBody List<String> studentIds);

}
