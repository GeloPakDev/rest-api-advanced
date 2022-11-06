package com.epam.esm.dto.converter.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class OrderDtoConverter implements DtoConverter<Order, OrderDto> {

    @Override
    public Order convertToEntity(OrderDto dto) {
        Order order = new Order();

        order.setId(dto.getId());
        order.setPrice(dto.getPrice());
        order.setUserId(dto.getUserId());
        order.setOrderDate(dto.getOrderDate());

        Set<GiftCertificate> gifts = new HashSet<>();
        for (Long giftId : dto.getGifts()) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(giftId);
            gifts.add(giftCertificate);
        }
        order.setGifts(gifts);
        return order;
    }

    @Override
    public OrderDto convertToDto(Order entity) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(entity.getId());
        orderDto.setPrice(entity.getPrice());
        orderDto.setOrderDate(entity.getOrderDate());
        orderDto.setUserId(entity.getId());
        Set<Long> gifts = new HashSet<>();
        for (GiftCertificate giftCertificate : entity.getGifts()) {
            Long id = giftCertificate.getId();
            gifts.add(id);
        }
        orderDto.setGifts(gifts);

        return orderDto;
    }
}
