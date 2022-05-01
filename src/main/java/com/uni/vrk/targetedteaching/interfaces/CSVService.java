package com.uni.vrk.targetedteaching.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {


    void saveApplications(MultipartFile file);
}
