package com.epam.esm.impl;

import com.epam.esm.AbstractDao;
import com.epam.esm.Order;
import com.epam.esm.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDaoImpl extends AbstractDao<Order, Long> implements OrderDao {

    @Autowired
    public OrderDaoImpl() {
        super(Order.class);
    }

    @Override
    public Page<Order> findUsersOrders(Long id, Pageable pageable) {
        //Get List of Orders
        List<Order> orderList = entityManager.createQuery("SELECT o FROM Order o WHERE o.userId = :id", Order.class)
                .setParameter("id", id)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        //Get total count of retrieved orders
        Long count = (Long) entityManager.createQuery("SELECT COUNT(o) FROM Order o WHERE o.userId = :id")
                .setParameter("id", id)
                .getSingleResult();

        return new PageImpl<>(orderList, pageable, count);
    }

    @Override
    public Optional<Order> findUserOrderById(Long userId, Long orderId) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.userId = :userId AND o.id = :orderId", Order.class)
                .setParameter("userId", userId)
                .setParameter("orderId", orderId)
                .getResultList().stream()
                .findFirst();
    }
}