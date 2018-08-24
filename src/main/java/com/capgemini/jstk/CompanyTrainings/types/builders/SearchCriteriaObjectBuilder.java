package com.capgemini.jstk.CompanyTrainings.types.builders;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;

import java.time.LocalDate;

public class SearchCriteriaObjectBuilder {
    private String title;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Integer minCost;
    private Integer maxCost;
    private String tag;

    public SearchCriteriaObjectBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public SearchCriteriaObjectBuilder setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
        return this;
    }

    public SearchCriteriaObjectBuilder setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
        return this;
    }

    public SearchCriteriaObjectBuilder setMinCost(Integer minCost) {
        this.minCost = minCost;
        return this;
    }

    public SearchCriteriaObjectBuilder setMaxCost(Integer maxCost) {
        this.maxCost = maxCost;
        return this;
    }

    public SearchCriteriaObjectBuilder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public SearchCriteriaObject buildSearchCriteriaObject() {
        return new SearchCriteriaObject(title, trainingType, trainingDate, minCost, maxCost, tag);
    }
}