package net.etg.registerservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Registration {

    @Id
    @Column(name = "registration_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationNo;

    private String studentId;

    @ElementCollection
    @CollectionTable(name = "registration_course",
                    joinColumns = @JoinColumn(name = "registration_no"))
    private List<String> courseCode = new ArrayList<>();

    private LocalDateTime registrationDate = LocalDateTime.now();
}
