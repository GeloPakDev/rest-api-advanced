package com.epam.esm.service;

import com.epam.esm.Tag;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TagService {

    Optional<Tag> findById(Long id);

    Page<Tag> findAll(int page, int size);

    Tag create(Tag entity);

    Tag delete(Long id);

    Optional<Tag> findByName(String name);

    Page<Tag> findTheMostPopularTagsOfUsesOrders(int page, int size);
}