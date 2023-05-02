package net.etg.registerservice.service.impl;

import lombok.AllArgsConstructor;
import net.etg.registerservice.dto.CourseDTO;
import net.etg.registerservice.dto.RegistrationDTO;
import net.etg.registerservice.dto.StudentDTO;
import net.etg.registerservice.entity.Registration;
import net.etg.registerservice.exception.NoCourseException;
import net.etg.registerservice.exception.NoStudentException;
import net.etg.registerservice.proxy.OpenFeignCourseProxy;
import net.etg.registerservice.proxy.OpenFeignStudentProxy;
import net.etg.registerservice.repository.RegistrationRepository;
import net.etg.registerservice.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Registration savedRegistration = registrationRepository.save(registration);
        return modelMapper.map(savedRegistration,RegistrationDTO.class);
    }


    @Override
    public List<CourseDTO> getCoursesByStudentId(String studentId){
        Optional<List<Registration>> optionalRegistrations = registrationRepository.getRegistrationsByStudentId(studentId);
        if(optionalRegistrations.isPresent()){
            List<Registration> registrationList = optionalRegistrations.get();
            List<String> courseCodes = new ArrayList<>();
            registrationList.forEach(
                    registration -> courseCodes.addAll(registration.getCourseCode())
            );
            return openFeignCourseProxy.getCourses(courseCodes).getBody();
        }
        throw new NoCourseException();
    }

    @Override
    public List<StudentDTO> getStudentsByCourse(String courseCode) {
        Optional<ArrayList<Long>> optionalRegistrationNos = registrationRepository.getRegistrationNosByCourseCode(courseCode);
        if(optionalRegistrationNos.isPresent()){
            List<Long> registrationNos = optionalRegistrationNos.get();
            List<String> studentIds = new ArrayList<>();
            registrationNos.forEach(
                    i -> studentIds.add(registrationRepository.findById(i).get().getStudentId())
            );
            return openFeignStudentProxy.getStudents(studentIds).getBody();
        }
        throw new NoStudentException();


    }
}
