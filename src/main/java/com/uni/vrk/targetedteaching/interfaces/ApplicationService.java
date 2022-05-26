package com.uni.vrk.targetedteaching.interfaces;

import com.uni.vrk.targetedteaching.dto.response.ApplicationResponse;
import com.uni.vrk.targetedteaching.dto.response.ListedApplicationResponse;
import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.dto.request.NewApplicationRequest;
import com.uni.vrk.targetedteaching.dto.request.UpdateApplicationRequest;

import java.util.List;

public interface ApplicationService {

    List<ApplicationResponse> getAllApplications();

    ApplicationResponse saveApplication(NewApplicationRequest applicationRequest);

    ApplicationResponse getApplication(String id);

    void updateApplication(String id, UpdateApplicationRequest updateApplicationRequest);

    void deleteApplication(String id);
}
