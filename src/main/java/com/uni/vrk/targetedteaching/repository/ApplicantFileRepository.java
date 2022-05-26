package com.uni.vrk.targetedteaching.repository;

import com.uni.vrk.targetedteaching.model.ApplicantFile;
import com.uni.vrk.targetedteaching.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
public interface ApplicantFileRepository extends JpaRepository<ApplicantFile, String> {
    Optional<List<ApplicantFile>> findAllByApplication(Application application);
}
