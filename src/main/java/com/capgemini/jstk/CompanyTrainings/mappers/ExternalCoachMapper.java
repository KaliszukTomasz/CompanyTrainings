package com.capgemini.jstk.CompanyTrainings.mappers;

import com.capgemini.jstk.CompanyTrainings.domain.ExternalCoachEntity;
import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.ExternalCoachTOBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExternalCoachMapper {

    public ExternalCoachTO mapExternalCoachEntity2ExternalCoachTO(ExternalCoachEntity externalCoachEntity){

        return new ExternalCoachTOBuilder()
                .setId(externalCoachEntity.getId())
                .setFirstName(externalCoachEntity.getFirstName())
                .setLastName(externalCoachEntity.getLastName())
                .setCompany(externalCoachEntity.getCompany())
                .setVersion(externalCoachEntity.getVersion())
                .buildExternalCoachTO();
    }
}
