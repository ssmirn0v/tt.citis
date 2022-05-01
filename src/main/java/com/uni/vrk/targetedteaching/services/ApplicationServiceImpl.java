package com.uni.vrk.targetedteaching.services;

import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.dto.request.NewApplicationRequest;
import com.uni.vrk.targetedteaching.dto.request.UpdateApplicationRequest;
import com.uni.vrk.targetedteaching.interfaces.ApplicationService;
import com.uni.vrk.targetedteaching.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;

    @Autowired
    private void setApplicationRepository(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    @Override
    public List<Application> getAllApplications() {
        log.info("Fetching all applications");
        return applicationRepository.findAll();
    }

    @Override
    public Application saveApplication(NewApplicationRequest applicationRequest) {
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
                .consentPersonalData(applicationRequest.isConsentPersonalData())
                .status(applicationRequest.getStatus())
                .build();
        return applicationRepository.save(application);
    }

    @Override
    public void updateApplication(String id, UpdateApplicationRequest updateApplicationRequest) {
        Application application = applicationRepository.findByApplicationId(id.toString())
                .orElseThrow(()-> new EntityNotFoundException("Application with id: " + id + " not found"));
        BeanUtils.copyProperties(updateApplicationRequest, application);
        log.info(updateApplicationRequest.toString());
        log.info(application.toString());
        applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(String id) {
        applicationRepository.deleteByApplicationId(id);
    }
}
