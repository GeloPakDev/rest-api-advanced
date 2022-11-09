package com.epam.esm.impl;

import com.epam.esm.AbstractDao;
import com.epam.esm.GiftCertificate;
import com.epam.esm.GiftCertificateDao;
import com.epam.esm.creator.QueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate, Long> implements GiftCertificateDao {
    private final QueryCreator<GiftCertificate> queryCreator;

    @Autowired
    public GiftCertificateDaoImpl(QueryCreator<GiftCertificate> queryCreator) {
        super(GiftCertificate.class);
        this.queryCreator = queryCreator;
    }

    @Override
    public Page<GiftCertificate> findWithFilters(MultiValueMap<String, String> fields, Pageable pageable) {
        //Used to create different parts of the query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //Declare type of row in the result
        CriteriaQuery<GiftCertificate> criteriaQuery = queryCreator.createGetQuery(fields, criteriaBuilder);
        //Create instance from the CriteriaQuery
        List<GiftCertificate> list = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        int count = entityManager.createQuery(criteriaQuery).getResultList().size();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }
}