package com.uni.vrk.targetedteaching.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ApplicantStatus {
    STATUS_1("Свободен"){
        @Override
        public List<ApplicantStatus> nextStatuses() {
            return Arrays.asList(STATUS_2, STATUS_4);
        }
    },
    STATUS_2("В работе") {
        @Override
        public List<ApplicantStatus> nextStatuses() {
            return Arrays.asList(STATUS_1, STATUS_3, STATUS_4);
        }
    },
    STATUS_3("Отобран") {
        @Override
        public List<ApplicantStatus> nextStatuses() {
            return Arrays.asList(STATUS_4);
        }
    },
    STATUS_4("Выбыл") {
        @Override
        public List<ApplicantStatus> nextStatuses() {
            return Collections.emptyList();
        }
    };

    private String name;

    ApplicantStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    public abstract List<ApplicantStatus> nextStatuses();
}
