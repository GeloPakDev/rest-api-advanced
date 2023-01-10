package com.epam.esm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EntityDao<E, K> {

    Optional<E> findById(K id);

    Page<E> findAll(Pageable pageable);

    E create(E entity);

    E deleteById(K id);

}
