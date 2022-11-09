package com.epam.esm.impl;

import com.epam.esm.AbstractDao;
import com.epam.esm.Tag;
import com.epam.esm.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDaoImpl extends AbstractDao<Tag, Long> implements TagDao {

    public static final String POPULAR =
            "SELECT t " +
                    "FROM Order o " +
                    "INNER JOIN o.gifts g " +
                    "INNER JOIN g.tags t " +
                    "WHERE o.userId IN (SELECT o.userId FROM Order o GROUP BY o.userId ORDER BY SUM(o.price))" +
                    "GROUP BY t.id " +
                    "ORDER BY COUNT(t.id)";
    public static final String POPULAR_COUNT =
            "SELECT COUNT(t) " +
                    "FROM Order o " +
                    "INNER JOIN o.gifts g " +
                    "INNER JOIN g.tags t " +
                    "WHERE o.userId IN (SELECT o.userId FROM Order o GROUP BY o.userId ORDER BY SUM(o.price))" +
                    "GROUP BY t.id " +
                    "ORDER BY COUNT(t.id)";


    @Autowired
    public TagDaoImpl() {
        super(Tag.class);
    }

    @Override
    public Page<Tag> findTheMostPopularTagsOfUsesOrders(Pageable pageable) {
        List<Tag> list = entityManager.createQuery(POPULAR, Tag.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(1)
                .getResultList();

        Long count = (Long) entityManager.createQuery(POPULAR_COUNT)
                .setMaxResults(1)
                .getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}
