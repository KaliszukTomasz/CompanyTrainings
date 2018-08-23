package com.capgemini.jstk.CompanyTrainings.dao;

import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;

import java.util.List;

public interface TrainingDaoCustom {

    List<TrainingEntity> findTrainingsByCriteria(SearchCriteriaObject searchCriteriaObject);
}
