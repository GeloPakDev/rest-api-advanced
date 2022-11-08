package com.epam.esm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public abstract class AbstractDao<E, K> {
    //Used to manage persistent entity instances
    @PersistenceContext
    protected EntityManager entityManager;
    protected final Class<E> entityType;

    public AbstractDao(Class<E> entityType) {
        this.entityType = entityType;
    }

    //READ methods
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    public Page<E> findAll(Pageable pageable) {
        String entityName = entityType.getSimpleName();
        List<E> list = entityManager.createQuery("SELECT e FROM " + entityName + " e", entityType)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long count = (Long) entityManager.createQuery("SELECT COUNT(o) FROM " + entityName + " o")
                .getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }

    public Optional<E> findByName(String name) {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() + " e WHERE e.name = :name", entityType)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }

    //DELETE methods
    public E deleteById(K id) {
        //Find object for deletion
        E entity = entityManager.find(entityType, id);
        //Delete this object
        entityManager.remove(entity);
        return entity;
    }

    //CREATE methods
    public E create(E entity) {
        //create new instance of type У
        entityManager.persist(entity);
        return entity;
    }

}