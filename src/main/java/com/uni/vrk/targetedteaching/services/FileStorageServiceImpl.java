package com.uni.vrk.targetedteaching.services;

import com.uni.vrk.targetedteaching.dto.response.ApplicantFileResponse;
import com.uni.vrk.targetedteaching.dto.response.FileResponse;
import com.uni.vrk.targetedteaching.model.ApplicantFile;
import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.repository.ApplicantFileRepository;
import com.uni.vrk.targetedteaching.repository.ApplicationRepository;
import com.uni.vrk.targetedteaching.interfaces.FileStorageService;
import com.uni.vrk.targetedteaching.services.helper.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final String PATH_FILE = "/file";
    private final boolean IS_LOCAL = true;

    @Autowired
    private ApplicantFileRepository applicantFileRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public ApplicantFileResponse store(MultipartFile file, String applicationId) throws IOException {

        Application application = getApplication(applicationId);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uuid = UUID.randomUUID().toString();
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/")
                .path(uuid)
                .toUriString();
        ApplicantFile applicantFile = new ApplicantFile(uuid, fileName, file.getContentType(),
                file.getBytes(), application, url, IS_LOCAL);
        application.getFiles().add(applicantFile);
        applicationRepository.save(application);

        ApplicantFileResponse applicantFileResponse = ApplicantFileResponse.builder()
                .id(applicantFile.getId())
                .name(applicantFile.getName())
                .url(applicantFile.getUrl())
                .type(applicantFile.getType())
                .build();

        return applicantFileResponse;
    }

    @Override
    public ApplicantFile getFile(String fileId) {
        ApplicantFile file = applicantFileRepository.getById(fileId);
        return file;
    }

    @Override
    public List<FileResponse> getAllFilesForApplication(String applicationId) {
        Application application = getApplication(applicationId);
        List<ApplicantFile> applicantFileList = applicantFileRepository.findAllByApplication(application)
                .orElseThrow(() -> new EntityNotFoundException("Files for application " +
                        applicationId + " were not found"));
        return StreamUtils.toStream(applicantFileList)
                .map(file -> {
                    String url = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/file")
                            .path(file.getId())
                            .toUriString();
                    return new FileResponse(
                            file.getName(),
                            url,
                            file.getType()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(String applicationId, String fileId) {
        Application application = getApplication(applicationId);
        List<ApplicantFile> applicantFiles = application.getFiles();
        for (ApplicantFile file: applicantFiles) {
            if (file.getId().equals(fileId)) {
                applicantFiles.remove(file);
                applicationRepository.save(application);
                return;
            }
        }
    }

    private Application getApplication(String applicationId) {
        return applicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application with id "
                        + applicationId + " was not found"));
    }
}
