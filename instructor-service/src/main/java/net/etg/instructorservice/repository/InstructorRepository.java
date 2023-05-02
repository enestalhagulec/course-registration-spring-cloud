package net.etg.instructorservice.repository;

import net.etg.instructorservice.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor,String > {

    @Query(value = "SELECT course_code FROM instructor_course WHERE instructor_id =:instructorId", nativeQuery = true)
    public Optional<List<String>> getCourseCodesForAnInstructor(String instructorId);

}
