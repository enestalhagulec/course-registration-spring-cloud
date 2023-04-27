package net.etg.studentservice.repository;

import net.etg.studentservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,String> {

}
