package com.uni.vrk.teachingcenter.dao;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleE name;



}
