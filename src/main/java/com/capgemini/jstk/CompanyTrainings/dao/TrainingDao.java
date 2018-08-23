package com.capgemini.jstk.CompanyTrainings.dao;

import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDao extends CrudRepository<TrainingEntity, Long>, TrainingDaoCustom {

    List<TrainingEntity> findAll();
    TrainingEntity save(TrainingEntity trainingEntity);
    TrainingEntity getOne(Long id);
    void delete(Long id);

}
