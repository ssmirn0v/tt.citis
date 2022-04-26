package com.uni.vrk.teachingcenter.repository;

import com.uni.vrk.teachingcenter.dao.UserC;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserC, Long> {

    public Optional<UserC> findUserCByEmail(String email);
    public boolean existsByEmail(String email);

}
