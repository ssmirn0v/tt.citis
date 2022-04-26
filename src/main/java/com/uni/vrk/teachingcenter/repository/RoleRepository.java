package com.uni.vrk.teachingcenter.repository;

import com.uni.vrk.teachingcenter.dao.Role;
import com.uni.vrk.teachingcenter.dao.RoleE;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(RoleE roleE);
}
