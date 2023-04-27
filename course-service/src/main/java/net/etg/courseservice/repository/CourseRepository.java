package net.etg.courseservice.repository;

import net.etg.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,String> {

    @Query("SELECT c FROM Course c WHERE c.instructorId=:instructorId")
    Optional<ArrayList<Course>> findCoursesByInstructorId(String instructorId);
}
