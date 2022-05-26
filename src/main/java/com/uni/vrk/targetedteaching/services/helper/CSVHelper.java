package com.uni.vrk.targetedteaching.services.helper;

import com.uni.vrk.targetedteaching.model.ApplicantFile;
import com.uni.vrk.targetedteaching.model.ApplicantStatus;
import com.uni.vrk.targetedteaching.model.Application;
import com.uni.vrk.targetedteaching.repository.ApplicationRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
public class CSVHelper {

    public final String TYPE = "text/csv";
    public final boolean IS_LOCAL = false;

    public boolean hasCSVFormat(MultipartFile file) {
        return !TYPE.equals(file.getContentType());

    }

    public List<Application> csvToApplicationsList(InputStream is) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build()); //CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build()
                ) {
            List<Application> applications = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();


            for (CSVRecord csvRecord : csvRecords) {
                List<ApplicantFile> applicantFiles = new ArrayList<>();
                Application application = Application.builder()
                        .applicationId(csvRecord.get(0))
                        .applicationTime(LocalDateTime.parse(
                                csvRecord.get(1).replace(" ", "T"),
                                DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .firstName(csvRecord.get(4))
                        .lastName(csvRecord.get(3))
                        .patronymic(csvRecord.get(5))
                        .dateOfBirth(csvRecord.get(6).isEmpty() | csvRecord.get(6).isBlank() ?
                                null : LocalDate.parse(csvRecord.get(6), DateTimeFormatter.ISO_LOCAL_DATE))
                        .snils(csvRecord.get(7))
                        .email(csvRecord.get(8))
                        .phoneNumber(csvRecord.get(9))
                        .status(ApplicantStatus.STATUS_1)
                        .build();

                for (int i = 11; i <= 15; i++) {
                    String file = csvRecord.get(i);
                    if (!file.isEmpty() && file != null) {
                        String fileName = getFilename(file)  ;
                        String contentType = getContentType(fileName);
                        String uuid = UUID.randomUUID().toString();
                        ApplicantFile applicantFile = new ApplicantFile(uuid, fileName,
                                contentType, application, file, IS_LOCAL);
                        applicantFiles.add(applicantFile);
                    }
                }

                application.setFiles(applicantFiles);

                applications.add(application);
            }
            return applications;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private String getContentType(String fileName) {
        String extension = "";
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = fileName.substring(lastDotIndex + 1);
        }
        return extension;
    }

    private String getFilename(String file) {
        int lastSlashIndex = file.lastIndexOf('/');
        if (lastSlashIndex > 0) {
            return file.substring(lastSlashIndex + 1);
        } else
            return file;
    }
}
