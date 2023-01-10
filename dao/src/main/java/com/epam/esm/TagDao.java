package com.epam.esm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagDao extends EntityDao<Tag, Long> {
    Optional<Tag> findByName(String name);

    Page<Tag> findTheMostPopularTagsOfUsesOrders(Pageable pageable);

}