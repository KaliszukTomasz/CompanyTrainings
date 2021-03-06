package com.capgemini.jstk.CompanyTrainings.mappers;

import com.capgemini.jstk.CompanyTrainings.domain.ExternalCoachEntity;
import com.capgemini.jstk.CompanyTrainings.domain.ExternalCoachEntityBuilder;
import com.capgemini.jstk.CompanyTrainings.types.ExternalCoachTO;
import com.capgemini.jstk.CompanyTrainings.types.builders.ExternalCoachTOBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExternalCoachMapper {

    public ExternalCoachTO mapExternalCoachEntity2ExternalCoachTO(ExternalCoachEntity externalCoachEntity){

        return new ExternalCoachTOBuilder()
                .setVersion(externalCoachEntity.getVersion())
                .setId(externalCoachEntity.getId())
                .setFirstName(externalCoachEntity.getFirstName())
                .setLastName(externalCoachEntity.getLastName())
                .setCompany(externalCoachEntity.getCompany())
                .setVersion(externalCoachEntity.getVersion())
                .buildExternalCoachTO();
    }

    public ExternalCoachEntity mapExternalCoachTO2ExternalCoachEntity(ExternalCoachTO externalCoachTO){

        return new ExternalCoachEntityBuilder()
                .setFirstName(externalCoachTO.getFirstName())
                .setLastName(externalCoachTO.getLastName())
                .setCompany(externalCoachTO.getCompany())
                .buildExternalCoachEntity();
    }

    public List<ExternalCoachTO> mapExternalCoachEntityList2ExtCoachTOList(List<ExternalCoachEntity> listEntitys) {

        return listEntitys.stream().map(coachEntity -> {
            return new ExternalCoachTOBuilder()
                    .setVersion(coachEntity.getVersion())
                    .setId(coachEntity.getId())
                    .setFirstName(coachEntity.getFirstName())
                    .setLastName(coachEntity.getLastName())
                    .setCompany(coachEntity.getCompany())
                    .buildExternalCoachTO();
        }).collect(Collectors.toList());
    }

}
