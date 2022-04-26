package com.uni.vrk.teachingcenter.services;

import com.uni.vrk.teachingcenter.dao.Application;
import com.uni.vrk.teachingcenter.interfaces.CSVService;
import com.uni.vrk.teachingcenter.repository.ApplicationRepository;
import com.uni.vrk.teachingcenter.services.helper.CSVHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
