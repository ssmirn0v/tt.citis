package com.uni.vrk.targetedteaching.repository;

import com.uni.vrk.targetedteaching.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Override
    <S extends Application> S save(S entity);

    Optional<Application> findByApplicationId(String uuid);

    void deleteByApplicationId(String uuid);

    boolean existsByApplicationId(String id);

}
