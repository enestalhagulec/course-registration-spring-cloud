package net.etg.registerservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.registerservice.dto.CourseDTO;
import net.etg.registerservice.dto.RegistrationDTO;
import net.etg.registerservice.dto.StudentDTO;
import net.etg.registerservice.entity.Registration;
import net.etg.registerservice.proxy.OpenFeignCourseProxy;
import net.etg.registerservice.proxy.OpenFeignStudentProxy;
import net.etg.registerservice.repository.RegistrationRepository;
import net.etg.registerservice.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final ModelMapper modelMapper;
    private final RegistrationRepository registrationRepository;
    private final OpenFeignCourseProxy openFeignCourseProxy;
    private final OpenFeignStudentProxy openFeignStudentProxy;


    @Override
    public RegistrationDTO register(RegistrationDTO registrationDTO) {
        Registration registration = modelMapper.map(registrationDTO,Registration.class);
        System.out.println(registration);
        Registration savedRegistration = registrationRepository.save(registration);
        return modelMapper.map(savedRegistration,RegistrationDTO.class);
    }

    @Override
    public List<CourseDTO> getCoursesByStudentId(String studentId){
        List<Registration> registrationList = registrationRepository.getRegistrationsByStudentId(studentId).get();
        System.out.println("\n\n\n\n\n*******\n\n" + registrationList + "\n\n\n");
        List<String> courseCodes = new ArrayList<>();
        for(int i=0; i<registrationList.size(); i++){
            courseCodes.addAll(registrationList.get(i).getCourseCode());
        }

        System.out.println("\n\n\n\n\n*******\n\n" + courseCodes + "\n\n\n");
        List<CourseDTO> courseDTOList = openFeignCourseProxy.getCourses(courseCodes).getBody();
        return courseDTOList;

    }

    @Override
    public List<StudentDTO> getStudentsByCourse(String courseCode) {
        List<Long> registrationNos = registrationRepository.getRegistrationNosByCourseCode(courseCode).get();
        List<String> studentIds = new ArrayList<>();
        for(int i = 0; i<registrationNos.size(); i++){
            Registration existingRegistration = registrationRepository.findById(registrationNos.get(i)).get();
            studentIds.add(existingRegistration.getStudentId());
        }
        return openFeignStudentProxy.getStudents(studentIds).getBody();

    }
}
