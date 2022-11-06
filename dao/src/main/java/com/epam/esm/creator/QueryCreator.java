package com.epam.esm.creator;

import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface QueryCreator<T> {

    CriteriaQuery<T> createGetQuery(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder);
}