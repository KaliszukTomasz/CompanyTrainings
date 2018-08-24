package com.capgemini.jstk.CompanyTrainings.types;

import com.capgemini.jstk.CompanyTrainings.enums.TrainingType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchCriteriaObject {

    private String title;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Integer minCost;
    private Integer maxCost;
    private String tag;

    public SearchCriteriaObject() {
    }

    public SearchCriteriaObject(String title, TrainingType trainingType, LocalDate trainingDate, Integer minCost, Integer maxCost, String tag) {
        this.title = title;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.tag = tag;
    }
}
