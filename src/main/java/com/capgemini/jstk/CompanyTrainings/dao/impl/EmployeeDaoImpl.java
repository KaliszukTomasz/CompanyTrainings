package com.capgemini.jstk.CompanyTrainings.dao.impl;

import com.capgemini.jstk.CompanyTrainings.dao.EmployeeDaoCustom;
import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.QEmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.QTrainingEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDaoCustom {
    @PersistenceContext
    EntityManager em;


    @Override
    public Double findNumerOfHoursEmployeeAsCoach(EmployeeEntity tempEmployeeEntity) {
        JPAQuery<TrainingEntity> query = new JPAQuery(em);
        QTrainingEntity trainingEntity = QTrainingEntity.trainingEntity;
        QEmployeeEntity employeeEntity = QEmployeeEntity.employeeEntity;
        return query.select(trainingEntity.duration.sum())
                .from(trainingEntity)
                .leftJoin(trainingEntity.employeesAsCoaches, employeeEntity)
                .where(employeeEntity.eq(tempEmployeeEntity))
                .fetchOne();

    }
}
