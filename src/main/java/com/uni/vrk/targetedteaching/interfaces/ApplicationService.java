package com.uni.vrk.targetedteaching.interfaces;

import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.dto.request.NewApplicationRequest;
import com.uni.vrk.targetedteaching.dto.request.UpdateApplicationRequest;

import java.util.List;

public interface ApplicationService {

    List<Application> getAllApplications();

    Application saveApplication(NewApplicationRequest applicationRequest);

    void updateApplication(String id, UpdateApplicationRequest updateApplicationRequest);

    void deleteApplication(String id);
}
