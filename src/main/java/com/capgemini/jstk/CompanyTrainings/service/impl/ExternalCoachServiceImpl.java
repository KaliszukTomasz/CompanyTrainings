package com.capgemini.jstk.CompanyTrainings.service.impl;


import com.capgemini.jstk.CompanyTrainings.dao.ExternalCoachDao;
import com.capgemini.jstk.CompanyTrainings.domain.ExternalCoachEntity;
import com.capgemini.jstk.CompanyTrainings.exceptions.NoSuchExternalCoachIdInDatabaseException;
import com.capgemini.jstk.CompanyTrainings.mappers.ExternalCoachMapper;
import com.capgemini.jstk.CompanyTrainings.service.ExternalCoachService;
import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ExternalCoachServiceImpl implements ExternalCoachService {

    @Autowired
    ExternalCoachDao externalCoachDao;

    @Autowired
    ExternalCoachMapper externalCoachMapper;

    /**
     * add new external coach to database
     * @param externalCoachTO given external coach
     * @return externalCoachTO with id from database
     */
    @Override
    public ExternalCoachTO addExternalCoachToDatabase(ExternalCoachTO externalCoachTO) {

        ExternalCoachEntity externalCoachEntity = externalCoachMapper.mapExternalCoachTO2ExternalCoachEntity(externalCoachTO);
        externalCoachEntity = externalCoachDao.save(externalCoachEntity);
        return externalCoachMapper.mapExternalCoachEntity2ExternalCoachTO(externalCoachEntity);

    }

    /**
     * update external coach with params from given TO object
     * @param externalCoachTO given TO object
     * @return updated TO object
     */
    @Override
    public ExternalCoachTO updateExternalCoachInDatabase(ExternalCoachTO externalCoachTO) {

        ExternalCoachEntity externalCoachEntity = externalCoachDao.findOne(externalCoachTO.getId());

        if (externalCoachEntity == null) {
            throw new NoSuchExternalCoachIdInDatabaseException("No such externalCoach in database");
        }
        if (!externalCoachEntity.getVersion().equals(externalCoachTO.getVersion())) {
            throw new OptimisticLockException();
        }

        if (externalCoachTO.getFirstName() != null) {
            externalCoachEntity.setFirstName(externalCoachTO.getFirstName());
        }
        if (externalCoachTO.getLastName() != null) {
            externalCoachEntity.setLastName(externalCoachTO.getLastName());
        }
        if (externalCoachTO.getCompany() != null) {
            externalCoachEntity.setCompany(externalCoachTO.getCompany());
        }
        externalCoachEntity = externalCoachDao.save(externalCoachEntity);

        return externalCoachMapper.mapExternalCoachEntity2ExternalCoachTO(externalCoachEntity);
    }

    /**
     * remove external coach from database with id from given TO object
     * @param externalCoachTO given TO object
     */
    @Override
    public void removeExternalCoachFromDatabase(ExternalCoachTO externalCoachTO) {

        if (externalCoachDao.findOne(externalCoachTO.getId()) == null) {
            throw new NoSuchExternalCoachIdInDatabaseException("No such externalCoach.ID in database!");
        }
        externalCoachDao.delete(externalCoachTO.getId());
    }

    /**
     * find list of ExternalCoachTO objects
     * @return List of ExternalCoachTO
     */
    @Override
    public List<ExternalCoachTO> findAllCoachList() {

        return externalCoachMapper.mapExternalCoachEntityList2ExtCoachTOList(externalCoachDao.findAll());

    }

    /**
     * find one externalCoachTO with id from given long
     * @param coachId given long
     * @return ExternalCoachTO
     */
    @Override
    public ExternalCoachTO findOne(Long coachId) {

        ExternalCoachEntity externalCoachEntity = externalCoachDao.findOne(coachId);
        if (externalCoachEntity == null) {
            return null;
        }
        return externalCoachMapper.mapExternalCoachEntity2ExternalCoachTO(externalCoachEntity);

    }

}
