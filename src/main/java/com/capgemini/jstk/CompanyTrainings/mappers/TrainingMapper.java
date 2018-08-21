package com.capgemini.jstk.CompanyTrainings.mappers;

import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.TrainingTOBuilder;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public TrainingTO mapTrainingEntity2TrainingTO(TrainingEntity trainingEntity){
        return new TrainingTOBuilder()
                .setId(trainingEntity.getId())
                .setCostPerStudent(trainingEntity.getCostPerStudent())
                .setDuration(trainingEntity.getDuration())
                .setStartDate(trainingEntity.getStartDate())
                .setEndDate(trainingEntity.getEndDate())
                .setTags(trainingEntity.getTags())
                .setTrainingCharacter(trainingEntity.getTrainingCharacter())
                .setTrainingName(trainingEntity.getTrainingName())
                .setTrainingType(trainingEntity.getTrainingType())
                .setVersion(trainingEntity.getVersion())
                .buildTrainingTO();
    }
}
