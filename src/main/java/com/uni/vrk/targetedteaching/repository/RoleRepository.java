package com.uni.vrk.targetedteaching.repository;

import com.uni.vrk.targetedteaching.model.Role;
import com.uni.vrk.targetedteaching.model.RoleE;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(RoleE roleE);
}
