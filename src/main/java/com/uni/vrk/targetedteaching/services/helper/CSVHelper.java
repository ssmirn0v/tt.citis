package com.uni.vrk.targetedteaching.services.helper;

import com.uni.vrk.targetedteaching.model.ApplicantStatus;
import com.uni.vrk.targetedteaching.model.Application;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Application> csvToApplicationsList(InputStream is) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build()); //CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build()
                ) {
            List<Application> applications = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
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
                        .consentPersonalData(true)
                        .file1(csvRecord.get(11))
                        .file2(csvRecord.get(12))
                        .file3(csvRecord.get(13))
                        .file4(csvRecord.get(14))
                        .file5(csvRecord.get(15))
                        .status(ApplicantStatus.STATUS_1)
                        .build();
                applications.add(application);
            }
            return applications;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
