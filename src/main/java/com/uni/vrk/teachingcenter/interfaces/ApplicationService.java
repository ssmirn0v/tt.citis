package com.uni.vrk.teachingcenter.interfaces;

import com.uni.vrk.teachingcenter.dao.Application;
import com.uni.vrk.teachingcenter.dto.request.NewApplicationRequest;
import com.uni.vrk.teachingcenter.dto.request.UpdateApplicationRequest;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    List<Application> getAllApplications();

    Application saveApplication(NewApplicationRequest applicationRequest);

    void updateApplication(String id, UpdateApplicationRequest updateApplicationRequest);

    void deleteApplication(String id);
}
