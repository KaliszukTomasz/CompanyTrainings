package com.capgemini.jstk.CompanyTrainings.dao;

import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDao extends JpaRepository<TrainingEntity, Long>, TrainingDaoCustom {

    List<TrainingEntity> findAll();
    TrainingEntity save(TrainingEntity trainingEntity);
    TrainingEntity getOne(Long id);
    void delete(Long id);

}
