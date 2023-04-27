package net.etg.studentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Student {

     @Id
     @Column(name = "id")
     private String studentId;

     @Column(name = "name")
     private String name;

     @Column(name = "last_name")
     private String lastName;

}
