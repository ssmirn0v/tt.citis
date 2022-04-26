package com.uni.vrk.teachingcenter.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserC {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NonNull
    private String userId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String patronymic;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String position;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public UserC(String firstName, String lastName, String patronymic, String email, String password) {
        this.userId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
    }

//    private String[] authorities;
}
