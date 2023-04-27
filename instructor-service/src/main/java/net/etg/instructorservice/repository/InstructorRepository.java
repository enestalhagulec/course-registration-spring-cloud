package net.etg.instructorservice.repository;

import net.etg.instructorservice.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor,String > {

}
