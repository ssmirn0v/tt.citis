package com.uni.vrk.teachingcenter.dao;

public enum ApplicantStatus {
    STATUS_1("Свободен"),
    STATUS_2("В работе"),
    STATUS_3("Отобран"),
    STATUS_4("Выбыл");
    
    private String name;

    ApplicantStatus(String ready) {
    }

    public String getName() {
        return this.name;
    }
}
