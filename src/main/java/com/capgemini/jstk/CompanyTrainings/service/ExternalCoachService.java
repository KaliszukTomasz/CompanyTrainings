package com.capgemini.jstk.CompanyTrainings.service;

import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;

import java.util.List;

public interface ExternalCoachService {
    ExternalCoachTO addExternalCoachToDatabase(ExternalCoachTO externalCoachTO);

    ExternalCoachTO updateExternalCoachInDatabase(ExternalCoachTO externalCoachTO);

    void removeExternalCoachFromDatabase(ExternalCoachTO externalCoachTO);

    List<ExternalCoachTO> findAllCoachList();

    ExternalCoachTO findOne(Long coachId);
}
