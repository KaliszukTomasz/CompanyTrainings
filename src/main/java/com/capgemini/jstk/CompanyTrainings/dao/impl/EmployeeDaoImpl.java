package com.capgemini.jstk.CompanyTrainings.dao.impl;

import com.capgemini.jstk.CompanyTrainings.dao.EmployeeDaoCustom;
import com.capgemini.jstk.CompanyTrainings.domain.EmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.QEmployeeEntity;
import com.capgemini.jstk.CompanyTrainings.domain.QTrainingEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDaoCustom {
    @PersistenceContext
    EntityManager em;


    @Override
    public Double findNumerOfHoursEmployeeAsCoachInYear(EmployeeEntity tempEmployeeEntity, int year) {
        JPAQuery<TrainingEntity> query = new JPAQuery(em);
        QTrainingEntity trainingEntity = QTrainingEntity.trainingEntity;
        QEmployeeEntity employeeEntity = QEmployeeEntity.employeeEntity;
        return query.select(trainingEntity.duration.sum())
                .from(trainingEntity)
                .leftJoin(trainingEntity.employeesAsCoaches, employeeEntity)
                .where(employeeEntity.eq(tempEmployeeEntity)
                        .and(trainingEntity.startDate.year().eq(year))
                        .and(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED)))
                .fetchOne();

    }

    @Override
    public List<TrainingEntity> findListOfTrainingsByOneEmployeeInPeriodOfTime(EmployeeEntity tempEmployeeEntity, LocalDate startDate, LocalDate endDate) {
        JPAQuery<TrainingEntity> query = new JPAQuery<>(em);
        QTrainingEntity trainingEntity = QTrainingEntity.trainingEntity;
        QEmployeeEntity employeeEntity = QEmployeeEntity.employeeEntity;
        return query.select(trainingEntity)
                .from(trainingEntity)
                .leftJoin(trainingEntity.employeesAsStudents, employeeEntity)
                .where(employeeEntity.id.eq(tempEmployeeEntity.getId())
                        .and(trainingEntity.endDate.between(startDate, endDate))
                        .and(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED)))
                .fetch();

    }

    @Override
    public Integer findTotalCostOfTrainingsByEmployee(Long employeeId) {
        JPAQuery<TrainingEntity> query = new JPAQuery<>(em);
        QTrainingEntity trainingEntity = QTrainingEntity.trainingEntity;
        QEmployeeEntity employeeEntity = QEmployeeEntity.employeeEntity;
        return query.select(trainingEntity.costPerStudent.sum())
                .from(trainingEntity)
                .leftJoin(trainingEntity.employeesAsStudents, employeeEntity)
                .where(employeeEntity.id.eq(employeeId)
                        .and(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED)))
                .fetchOne();
    }

    @Override
    public List<EmployeeEntity> findEmployeesWithLongestTimeOnTrainingsAsStudents() {
        JPAQuery<TrainingEntity> query = new JPAQuery<>(em);
        JPAQuery<TrainingEntity> query2 = new JPAQuery<>(em);
        QTrainingEntity trainingEntity = QTrainingEntity.trainingEntity;
        QEmployeeEntity employeeEntity = QEmployeeEntity.employeeEntity;
        Double maxSumDuration = query.select(trainingEntity.duration.sum())
                .from(trainingEntity)
                .leftJoin(trainingEntity.employeesAsStudents, employeeEntity)
                .where(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED))
                .groupBy(employeeEntity)
                .orderBy(trainingEntity.duration.sum().desc())
                .limit(1)
                .fetchOne();
        return query2.select(employeeEntity)
                .from(trainingEntity)
                .leftJoin(trainingEntity.employeesAsStudents, employeeEntity)
                .where(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED))
                .groupBy(employeeEntity)
                .having(trainingEntity.duration.sum().eq(maxSumDuration))
                .fetch();
    }
}
