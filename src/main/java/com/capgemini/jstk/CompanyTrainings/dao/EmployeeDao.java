package com.capgemini.jstk.CompanyTrainings.dao;

import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAll();
    EmployeeEntity save(EmployeeEntity employeeEntity);



}
