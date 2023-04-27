package net.etg.instructorservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import net.etg.instructorservice.dto.CourseDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "instructor")
public class Instructor {

    @Id
    @Column(name = "instructor_id")
    private String instructorId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @ElementCollection
    @CollectionTable(name = "instructor_course",
            joinColumns = @JoinColumn(name = "instructor_id"))
    private List<String> courseCode = new ArrayList<>();


    public void addCourse(String courseCode){
        this.courseCode.add(courseCode);
    }


}
