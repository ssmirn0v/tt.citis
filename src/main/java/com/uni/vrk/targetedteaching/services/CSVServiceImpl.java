package com.uni.vrk.targetedteaching.services;

import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.interfaces.CSVService;
import com.uni.vrk.targetedteaching.repository.ApplicationRepository;
import com.uni.vrk.targetedteaching.services.helper.CSVHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CSVServiceImpl implements CSVService {
    ApplicationRepository applicationRepository;

    @Autowired
    private void setApplicationRepository(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    public void saveApplications(MultipartFile file) {
        try {
            List<Application> applications = CSVHelper.csvToApplicationsList(file.getInputStream());

            for (Application application : applications) {
                if (!applicationRepository.existsByApplicationId(application.getApplicationId())) {
                    applicationRepository.save(application);
                }
            }
        } catch (IOException e) {
            log.error("Fail to save applications in database");
            throw new RuntimeException("Fail to save applications from csv: " + e.getMessage());
        }
    }
}
