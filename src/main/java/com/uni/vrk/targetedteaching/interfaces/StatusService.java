package com.uni.vrk.targetedteaching.interfaces;

import com.uni.vrk.targetedteaching.dto.request.AssignRequest;

public interface StatusService {
    void assignApplication(String applicationId, AssignRequest assignRequest);


    void selectApplication(String applicationId);

    void dropApplication(String applicationId);
}
