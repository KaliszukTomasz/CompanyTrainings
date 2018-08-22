package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.dao.TrainingDao;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchTrainingIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.mappers.TrainingMapper;
import com.capgemini.jstk.CompanyTrainings.types.EmployeeTO;
import com.capgemini.jstk.CompanyTrainings.types.TrainingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrainingService {

    @Autowired
    TrainingDao trainingDao;
    @Autowired
    TrainingMapper trainingMapper;

    public TrainingTO addTrainingTOToDatabase(TrainingTO trainingTO) {

        TrainingEntity trainingEntity = trainingMapper.mapTrainingTO2TrainingEntity(trainingTO);
        trainingEntity = trainingDao.save(trainingEntity);
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);

    }

    public TrainingTO updateTrainingInDatabase(TrainingTO trainingTO) {

        TrainingEntity trainingEntity = trainingDao.findOne(trainingTO.getId());
        if (trainingEntity == null) {
            throw new NoSuchTrainingIdInDatabaseException("No such training.id in database!");
        }

        if (trainingTO.getVersion() != null) {
            trainingEntity.setVersion(trainingTO.getVersion());
        }

        if (trainingTO.getCostPerStudent() != null) {
            trainingEntity.setCostPerStudent(trainingTO.getCostPerStudent());
        }

        if (trainingTO.getDuration() != null) {
            trainingEntity.setDuration(trainingTO.getDuration());
        }

        if (trainingTO.getStartDate() != null) {
            trainingEntity.setStartDate(trainingTO.getStartDate());
        }

        if (trainingTO.getEndDate() != null) {
            trainingEntity.setEndDate(trainingTO.getEndDate());
        }

        if (trainingTO.getTags() != null) {
            trainingEntity.setTags(trainingTO.getTags());
        }

        if (trainingTO.getTrainingCharacter() != null) {
            trainingEntity.setTrainingCharacter(trainingTO.getTrainingCharacter());
        }

        if (trainingTO.getTrainingName() != null) {
            trainingEntity.setTrainingName(trainingTO.getTrainingName());
        }

        if (trainingTO.getTrainingType() != null) {
            trainingEntity.setTrainingType(trainingTO.getTrainingType());
        }

        trainingEntity = trainingDao.save(trainingEntity);
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);
    }

    public void removeTrainingFromDatabase(TrainingTO trainingTO) {

        if (trainingDao.findOne(trainingTO.getId()) == null) {
            throw new NoSuchTrainingIdInDatabaseException("No such training.ID in database!");
        }
        trainingDao.delete(trainingTO.getId());
    }

    public List<TrainingTO> findAllTrainingList() {

        return trainingMapper.mapTrainingEntityList2TrainingTOList(trainingDao.findAll());

    }

    public TrainingTO findOne(Long trainingID) {

        TrainingEntity trainingEntity = trainingDao.findOne(trainingID);
        if (trainingEntity == null) {
            return null;
        }
        return trainingMapper.mapTrainingEntity2TrainingTO(trainingEntity);
    }

    public void addCoachToTraining(TrainingTO trainingTO, EmployeeTO employeeTO){



    }
}
