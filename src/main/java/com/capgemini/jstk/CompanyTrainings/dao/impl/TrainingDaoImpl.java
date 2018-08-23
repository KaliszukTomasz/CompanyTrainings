package com.capgemini.jstk.CompanyTrainings.dao.impl;

import com.capgemini.jstk.CompanyTrainings.dao.TrainingDaoCustom;
import com.capgemini.jstk.CompanyTrainings.domain.TrainingEntity;
import com.capgemini.jstk.CompanyTrainings.types.SearchCriteriaObject;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingDaoImpl implements TrainingDaoCustom {
//    private static SessionFactory factory;


    @Override
    public List<TrainingEntity> findTrainingsByCriteria(SearchCriteriaObject searchCriteriaObject) {
        return null;
    }
}


//        Session session = HibernateUtil.
//        Session session = HibernateUtil.getHibernateSession();
//        factory = new Configuration().configure().buildSessionFactory();
//        Session session = factory.openSession();
//        Criteria cr = session.createCriteria(TrainingEntity.class);
//
//        if (searchCriteriaObject.getTitle() != null) {
//            Criterion title = Restrictions.ilike("trainingName", "%" + searchCriteriaObject.getTitle()+ "%");
//            cr.add(title);
//        }
//        if (searchCriteriaObject.getTrainingType() != null) {
//            Criterion trainingType = Restrictions.ilike("trainingType", searchCriteriaObject.getTrainingType());
//            cr.add(trainingType);
//        }
//        if (searchCriteriaObject.getTrainingDate() != null) {
//            Criterion trainingDateStart = Restrictions.le("startDate", searchCriteriaObject.getTrainingDate());
//            Criterion trainingDateEnd = Restrictions.gt("endDate", searchCriteriaObject.getTrainingDate());
//            LogicalExpression andExp1 = Restrictions.and(trainingDateStart, trainingDateEnd);
//            cr.add(andExp1);
//        }
//        if (searchCriteriaObject.getMinCost() != null && searchCriteriaObject.getMinCost() != null) {
//            Criterion minPrice = Restrictions.gt("costPerStudent", searchCriteriaObject.getMinCost());
//            Criterion maxPrice = Restrictions.le("costPerStudent", searchCriteriaObject.getMaxCost());
//            LogicalExpression andExp2 = Restrictions.and(minPrice, maxPrice);
//            cr.add(andExp2);
//        } else {
//            if (searchCriteriaObject.getMinCost() != null) {
//                Criterion minPrice = Restrictions.gt("costPerStudent", searchCriteriaObject.getMinCost());
//                cr.add(minPrice);
//            }
//            if (searchCriteriaObject.getMaxCost() != null) {
//                Criterion maxPrice = Restrictions.le("costPerStudent", searchCriteriaObject.getMaxCost());
//                cr.add(maxPrice);
//            }
//        }
//        if (searchCriteriaObject.getTag() != null) {
//            Criterion tag = Restrictions.ilike("tags", "%" + searchCriteriaObject.getTag() + "%");
//            cr.add(tag);
//        }

//        return cr.list();
//    }

