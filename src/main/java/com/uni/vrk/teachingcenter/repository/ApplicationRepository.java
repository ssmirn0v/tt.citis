package com.uni.vrk.teachingcenter.repository;

import com.uni.vrk.teachingcenter.dao.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Override
    <S extends Application> S save(S entity);

    Optional<Application> findByApplicationId(String uuid);

    void deleteByApplicationId(String uuid);

    boolean existsByApplicationId(String id);
}
