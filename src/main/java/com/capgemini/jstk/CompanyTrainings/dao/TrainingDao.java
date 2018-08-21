package com.capgemini.jstk.CompanyTrainings.dao;

import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingDao extends CrudRepository<EmployeeEntity, Long> {
}
