package com.capgemini.jstk.CompanyTrainings.dao;

import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.ExternalCoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalCoachDao extends JpaRepository<ExternalCoachEntity, Long> {


    ExternalCoachEntity save(ExternalCoachEntity externalCoachEntity);
    ExternalCoachEntity findOne(Long id);
    void delete(Long id);
    List<ExternalCoachEntity> findAll();

}
