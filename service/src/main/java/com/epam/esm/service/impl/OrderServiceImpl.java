package com.epam.esm.service.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.GiftCertificateDao;
import com.epam.esm.Order;
import com.epam.esm.OrderDao;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final GiftCertificateDao giftCertificateDao;

    public OrderServiceImpl(OrderDao orderDao, GiftCertificateDao giftCertificateDao) {
        this.orderDao = orderDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public Page<Order> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderDao.findAll(pageable);
    }

    @Override
    public Page<Order> findUsersOrders(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderDao.findUsersOrders(id, pageable);
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public Optional<Order> findUserOrderById(Long userId, Long orderId) {
        return orderDao.findUserOrderById(userId, orderId);
    }

    @Override
    @Transactional
    public Order createOrder(Long userId, Order order) {
        //Set userID to order form the Request
        order.setUserId(userId);
        //Get all gift id's from the Order
        Set<GiftCertificate> gifts = new HashSet<>();
        for (GiftCertificate giftCertificate : order.getGifts()) {
            //Get all gifts from the DB
            Long giftId = giftCertificate.getId();
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(giftId);
            if (optionalGiftCertificate.isEmpty()) {
                throw new NoSuchEntityException(String.valueOf(giftId));
            } else {
                GiftCertificate gift = optionalGiftCertificate.get();
                gifts.add(gift);
            }
        }
        order.setGifts(gifts);
        return orderDao.create(order);
    }
}
