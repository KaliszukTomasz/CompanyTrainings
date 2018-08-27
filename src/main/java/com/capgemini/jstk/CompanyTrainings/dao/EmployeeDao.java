package com.capgemini.jstk.CompanyTrainings.dao;

import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<EmployeeEntity, Long>, EmployeeDaoCustom {

    List<EmployeeEntity> findAll();
    EmployeeEntity save(EmployeeEntity employeeEntity);
    EmployeeEntity findOne(Long id);
    void delete(Long id);


}
