package com.uni.vrk.targetedteaching.interfaces;

import com.uni.vrk.targetedteaching.dto.request.AssignRequest;
import com.uni.vrk.targetedteaching.dto.response.UserResponse;

public interface StatusService {
    UserResponse assignApplication(String applicationId);


    void selectApplication(String applicationId);

    void dropApplication(String applicationId);

    void freeApplication(String applicationId);
}
