package com.capgemini.jstk.CompanyTrainings.dao.impl;

import com.capgemini.jstk.CompanyTrainings.dao.TrainingDaoCustom;
import com.capgemini.jstk.CompanyTrainings.domain.QTrainingEntity;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.enums.TrainingStatus;
import com.capgemini.jstk.CompanyTrainings.exceptions.SerachCriteriaObjectIsNullException;
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

    /**
     * find training by criteria from given critObj
     *
     * @param critObj given SearchCriteriaObject with:
     *                String title;
     *                TrainingType trainingType;
     *                LocalDate trainingDate;
     *                Integer minCost;
     *                Integer maxCost;
     *                String tag;
     * @return list of trainingEntity
     */
    @Override
    public List<TrainingEntity> findTrainingsByCriteria(SearchCriteriaObject critObj) {

        if (critObj == null) {
            throw new SerachCriteriaObjectIsNullException("SearchCriteriaObjectCantBeNull");
        }

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

    /**
     * find trainings with the highest edition - with max number of trainings with the same title
     * @return list of TrainingEntity objects
     */
    @Override
    public List<TrainingEntity> findTrainingsWithTheHighestEdition() {
        JPAQuery<TrainingEntity> query = new JPAQuery(em);
        JPAQuery<TrainingEntity> query2 = new JPAQuery(em);
        JPAQuery<TrainingEntity> query3 = new JPAQuery(em);
        QTrainingEntity trainingEntity = QTrainingEntity.trainingEntity;

        Long maxCount = query.select(trainingEntity.count())
                .from(trainingEntity)
                .where(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED))
                .groupBy(trainingEntity.trainingName)
                .orderBy(trainingEntity.count().desc())
                .limit(1)
                .fetchOne();

        List<String> titleMaxCountList = query2.select(trainingEntity.trainingName)
                .from(trainingEntity)
                .where(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED))
                .groupBy(trainingEntity.trainingName)
                .having(trainingEntity.count().eq(maxCount))
                .fetch();

        return query3.select(trainingEntity)
                .from(trainingEntity)
                .where(trainingEntity.trainingName.in(titleMaxCountList)
                        .and(trainingEntity.trainingStatus.ne(TrainingStatus.CANCELED)))
                .fetch();
    }
}