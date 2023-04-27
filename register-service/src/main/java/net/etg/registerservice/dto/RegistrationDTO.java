package net.etg.registerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegistrationDTO {

    private String studentId;
    private List<String> courseCode;

}
