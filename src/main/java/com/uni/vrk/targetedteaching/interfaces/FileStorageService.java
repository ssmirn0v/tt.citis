package com.uni.vrk.targetedteaching.interfaces;

import com.uni.vrk.targetedteaching.dto.response.ApplicantFileResponse;
import com.uni.vrk.targetedteaching.dto.response.FileResponse;
import com.uni.vrk.targetedteaching.model.ApplicantFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {
    ApplicantFileResponse store(MultipartFile file, String applicationId) throws IOException;
    ApplicantFile getFile(String fileId);
    List<FileResponse> getAllFilesForApplication(String applicationId);
    void deleteFile(String applicationId, String fileId);
}
