package com.uni.vrk.targetedteaching.repository;

import com.uni.vrk.targetedteaching.model.UserC;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserC, Long> {

    Optional<UserC> findUserCByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UserC> findUserCByUserId(String userId);



}
