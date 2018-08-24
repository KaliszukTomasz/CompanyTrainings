package com.capgemini.jstk.CompanyTrainings.dao.impl;

import com.capgemini.jstk.CompanyTrainings.dao.TrainingDaoCustom;
import com.capgemini.jstk.CompanyTrainings.domain.QTrainingEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TrainingDaoImpl implements TrainingDaoCustom {

    @PersistenceContext
    EntityManager em;


    @Override
    public List<TrainingEntity> findTrainingsByCriteria(SearchCriteriaObject critObj) {
        JPAQuery<TrainingEntity> query = new JPAQuery(em);
        QTrainingEntity trainingEntity = QTrainingEntity.trainingEntity;

        BooleanBuilder condition = new BooleanBuilder();
        if (critObj.getTitle() != null) {
            condition.and(trainingEntity.trainingName.eq(critObj.getTitle()));
        }
        if (critObj.getTrainingType() != null) {
            condition.and(trainingEntity.trainingType.eq(critObj.getTrainingType()));
        }
        if (critObj.getTrainingDate() != null) {
            condition.and(trainingEntity.startDate.before(critObj.getTrainingDate()));
            condition.and(trainingEntity.endDate.after(critObj.getTrainingDate()));
        }
        if (critObj.getMinCost() != null) {
            condition.and(trainingEntity.costPerStudent.goe(critObj.getMinCost()));
        }
        if (critObj.getMaxCost() != null) {
            condition.and(trainingEntity.costPerStudent.loe(critObj.getMaxCost()));
        }
        if (critObj.getTag() != null) {
            condition.and(trainingEntity.tags.like("%" + critObj.getTag() + "%"));
        }

        return query.from(trainingEntity).where(condition).fetch();
    }
}