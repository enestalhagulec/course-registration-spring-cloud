package net.etg.registerservice.repository;

import net.etg.registerservice.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration,Long> {

    @Query("SELECT r FROM Registration r WHERE r.studentId = :studentId")
    Optional<List<Registration>> getRegistrationsByStudentId(String studentId);

    @Query(value = "SELECT registration_no FROM registration_course WHERE course_code = :courseCode", nativeQuery = true)
    Optional<ArrayList<Long>> getRegistrationNosByCourseCode(String courseCode);
}
