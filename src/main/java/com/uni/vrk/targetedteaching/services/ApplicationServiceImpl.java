package com.uni.vrk.targetedteaching.services;

import com.uni.vrk.targetedteaching.dto.response.ApplicantFileResponse;
import com.uni.vrk.targetedteaching.dto.response.ApplicationResponse;
import com.uni.vrk.targetedteaching.dto.response.ListedApplicationResponse;
import com.uni.vrk.targetedteaching.dto.response.UserResponse;
import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.dto.request.NewApplicationRequest;
import com.uni.vrk.targetedteaching.dto.request.UpdateApplicationRequest;
import com.uni.vrk.targetedteaching.interfaces.ApplicationService;
import com.uni.vrk.targetedteaching.model.Role;
import com.uni.vrk.targetedteaching.model.RoleE;
import com.uni.vrk.targetedteaching.model.UserC;
import com.uni.vrk.targetedteaching.repository.ApplicationRepository;
import com.uni.vrk.targetedteaching.repository.RoleRepository;
import com.uni.vrk.targetedteaching.repository.UserRepository;
import com.uni.vrk.targetedteaching.services.helper.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private void setApplicationRepository(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    @Override
    public List<ApplicationResponse> getAllApplications() {
        log.info("Fetching all applications");
        return StreamUtils.toStream(applicationRepository.findAll())
                .map(this::transformToResponse)
                .collect(Collectors.toList());

    }

    @Override
    public ApplicationResponse saveApplication(NewApplicationRequest applicationRequest) {
        Application application = Application.builder()
                .applicationId(UUID.randomUUID().toString())
                .applicationTime(LocalDateTime.now())
                .lastName(applicationRequest.getLastName())
                .firstName(applicationRequest.getFirstName())
                .patronymic(applicationRequest.getPatronymic())
                .email(applicationRequest.getEmail())
                .phoneNumber(applicationRequest.getPhoneNumber())
                .dateOfBirth(applicationRequest.getDateOfBirth())
                .snils(applicationRequest.getSnils())
                .university(applicationRequest.getUniversity())
                .direction(applicationRequest.getDirection())
                .status(applicationRequest.getStatus())
                .build();

        application = applicationRepository.save(application);

        return transformToResponse(application);
    }

    @Override
    public ApplicationResponse getApplication(String id) {
        Application application = applicationRepository.findByApplicationId(id.toString())
                .orElseThrow(()-> new EntityNotFoundException("Application with id: " + id + " not found"));

        return transformToResponse(application);
    }

    @Override
    public void updateApplication(String id, UpdateApplicationRequest updateApplicationRequest) {
        Application application = applicationRepository.findByApplicationId(id.toString())
                .orElseThrow(()-> new EntityNotFoundException("Application with id: " + id + " not found"));
        BeanUtils.copyProperties(updateApplicationRequest, application);
        UserResponse user = updateApplicationRequest.getSupervisor();
        if (user != null) {
            UserC userC = userRepository.findUserCByUserId(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            application.setSupervisor(userC);
            userC.getApplications().add(application);
            userRepository.save(userC);
        }
        log.info(updateApplicationRequest.toString());
        log.info(application.toString());
        applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(String id) {
        Application application = applicationRepository.findByApplicationId(id.toString())
                .orElseThrow(()-> new EntityNotFoundException("Application with id: " + id + " not found"));
        UserC supervisor = application.getSupervisor();
        if (supervisor != null) {
            supervisor = userRepository.findById(supervisor.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            supervisor.getApplications().remove(application);
            userRepository.save(supervisor);
        }
        applicationRepository.deleteByApplicationId(id);
    }

    @Override
    public List<UserResponse> getAllAnalysts() {
        Role roleAnalyst = roleRepository.findByName(RoleE.ROLE_ANALYST)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        List<UserC> analysts = userRepository.findAllByRolesContaining(roleAnalyst);
        if (analysts != null) {
            List<UserResponse> analystsResponse = StreamUtils.toStream(analysts)
                    .map(analyst -> transformToUserResponse(analyst))
                    .collect(Collectors.toList());
            return analystsResponse;

        }
        return new ArrayList<>();
    }

    private ApplicationResponse transformToResponse(Application application) {
        List<ApplicantFileResponse> files = new ArrayList<>();

        if (application.getFiles() != null) {
            files = StreamUtils.toStream(application.getFiles())
                    .map(file -> ApplicantFileResponse.builder()
                            .id(file.getId())
                            .name(file.getName())
                            .type(file.getType())
                            .url(file.getUrl())
                            .build()
                    )
                    .collect(Collectors.toList());
        }


        ApplicationResponse applicationResponse = ApplicationResponse.builder()
                .applicationId(application.getApplicationId())
                .applicationTime(application.getApplicationTime())
                .snils(application.getSnils())
                .dateOfBirth(application.getDateOfBirth())
                .direction(application.getDirection())
                .email(application.getEmail())
                .firstName(application.getFirstName())
                .lastName(application.getLastName())
                .patronymic(application.getPatronymic())
                .phoneNumber(application.getPhoneNumber())
                .university(application.getUniversity())
                .files(files)
                .supervisor(transformToUserResponse(application.getSupervisor()))
                .status(application.getStatus())
                .build();
        return applicationResponse;

    }

    private UserResponse transformToUserResponse(UserC user) {
        UserResponse userResponse = null;
        if (user != null) {
            userResponse = UserResponse.builder()
                    .id(user.getUserId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .patronymic(user.getPatronymic())
                    .position(user.getPosition())
                    .build();
        }
        return userResponse;
    }
}
