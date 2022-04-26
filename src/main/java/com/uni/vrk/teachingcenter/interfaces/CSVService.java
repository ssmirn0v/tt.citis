package com.uni.vrk.teachingcenter.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {


    void saveApplications(MultipartFile file);
}
