package com.uni.vrk.targetedteaching.services;

import com.uni.vrk.targetedteaching.dto.request.AssignRequest;
import com.uni.vrk.targetedteaching.exception.ApplicationNotBelongException;
import com.uni.vrk.targetedteaching.exception.StatusTransitionNotPossibleException;
import com.uni.vrk.targetedteaching.interfaces.StatusService;
import com.uni.vrk.targetedteaching.model.ApplicantStatus;
import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.model.UserC;
import com.uni.vrk.targetedteaching.repository.ApplicationRepository;
import com.uni.vrk.targetedteaching.repository.UserRepository;
import com.uni.vrk.targetedteaching.security.UserDetailsImpl;
import com.uni.vrk.targetedteaching.services.helper.StreamUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class StatusServiceImpl implements StatusService {

    ApplicationRepository applicationRepository;
    UserRepository userRepository;
    private final SecurityContext securityContext = SecurityContextHolder.getContext();

    @Override
    public void assignApplication(String applicationId, AssignRequest assignRequest) {
        ApplicantStatus targetStatus = ApplicantStatus.STATUS_2;
        Application application = applicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new EntityNotFoundException());
        ApplicantStatus currentStatus = application.getStatus();
        if (checkIfTransitionIsPossible(currentStatus, targetStatus)) {
            UserC user = userRepository.findUserCByUserId(assignRequest.getSupervisorId())
                    .orElseThrow(() -> new EntityNotFoundException("User with id "
                            + assignRequest.getSupervisorId() + " was not found"));
            user.getApplications().add(application);
            userRepository.save(user);
        } else {
            throw new StatusTransitionNotPossibleException(String.format("Status transition" +
                    " from '%s' to '%s' in not possible", currentStatus.getName(), targetStatus.getName()));
        }
    }

    @Override
    public void selectApplication(String applicationId) {
        final ApplicantStatus targetStatus = ApplicantStatus.STATUS_3;


        if (checkBelongingApplicationToUser(applicationId)) {
            Application application = applicationRepository.findByApplicationId(applicationId)
                    .orElseThrow(() -> new EntityNotFoundException("Application with id "
                            + applicationId + " was not found"));
            final ApplicantStatus currentStatus = application.getStatus();
            if (checkIfTransitionIsPossible(currentStatus, targetStatus)) {
                application.setStatus(targetStatus);
            } else {
                throw new StatusTransitionNotPossibleException(String.format("Status transition" +
                        " from '%s' to '%s' in not possible", currentStatus.getName(), targetStatus.getName()));
            }

        } else {
            throw new ApplicationNotBelongException(String.format("Application with id '%s'" +
                    " not belong to current user", applicationId));
        }
    }

    @Override
    public void dropApplication(String applicationId) {
        final ApplicantStatus targetStatus = ApplicantStatus.STATUS_4;

        Application application = applicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application with id "
                        + applicationId + " was not found"));
        if (ApplicantStatus.STATUS_1.equals(application.getStatus()) || checkBelongingApplicationToUser(applicationId)) {
            final ApplicantStatus currentStatus = application.getStatus();
            if (checkIfTransitionIsPossible(currentStatus, targetStatus)) {
                application.setStatus(targetStatus);
            } else {
                throw new StatusTransitionNotPossibleException(String.format("Status transition" +
                        " from '%s' to '%s' in not possible", currentStatus.getName(), targetStatus.getName()));
            }
        } else {
            throw new ApplicationNotBelongException(String.format("Application with id '%s'" +
                    " not belong to current user", applicationId));
        }


    }

    private boolean checkBelongingApplicationToUser(String applicationId) {

        final SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetailsImpl userDetails = (UserDetailsImpl) securityContext.getAuthentication().getPrincipal();
        String userId = userDetails.getId();

        UserC user = userRepository.findUserCByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id "
                        + userId + " was not found"));
        Set<Application> applicationSet = user.getApplications();
        return StreamUtils.toStream(applicationSet)
                .map(application -> application.getApplicationId())
                .anyMatch(id -> id.equals(applicationId));
    }

    private boolean checkIfTransitionIsPossible(ApplicantStatus currentStatus, ApplicantStatus targetStatus) {
        List<ApplicantStatus> possibleTransitions = currentStatus.nextStatuses();
        return possibleTransitions.contains(targetStatus);
    }
}
