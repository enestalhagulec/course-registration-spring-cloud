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
        List<String> courseCodes = new ArrayList<>();

        registrationList.forEach(
                registration -> courseCodes.addAll(registration.getCourseCode())
        );
        List<CourseDTO> courseDTOList = openFeignCourseProxy.getCourses(courseCodes).getBody();
        return courseDTOList;

    }

    @Override
    public List<StudentDTO> getStudentsByCourse(String courseCode) {
        List<Long> registrationNos = registrationRepository.getRegistrationNosByCourseCode(courseCode).get();
        List<String> studentIds = new ArrayList<>();
        registrationNos.forEach(
                i -> studentIds.add(registrationRepository.findById(i).get().getStudentId())
        );
        return openFeignStudentProxy.getStudents(studentIds).getBody();

    }
}
