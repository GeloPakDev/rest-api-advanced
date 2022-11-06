package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static com.epam.esm.util.QueryParam.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class OrderHateoasAdder implements HateoasAdder<OrderDto> {
    public static final Class<OrderController> CONTROLLER_CLASS = OrderController.class;

    @Override
    public void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(CONTROLLER_CLASS).findAllOrders(DEFAULT_MIN_PAGE, DEFAULT_MIN_SIZE)).withSelfRel());
        orderDto.add(linkTo(methodOn(CONTROLLER_CLASS).findOrderById(orderDto.getId())).withRel("findById"));
        orderDto.add(linkTo(methodOn(CONTROLLER_CLASS).findAllUserOrders(orderDto.getUserId(), DEFAULT_MIN_PAGE, DEFAULT_MIN_SIZE)).withRel("userOrders"));
        orderDto.add(linkTo(methodOn(CONTROLLER_CLASS).findUserOrderById(orderDto.getUserId(), orderDto.getId())).withRel("userOrderById"));
        orderDto.add(linkTo(methodOn(CONTROLLER_CLASS).createGiftCertificate(orderDto.getUserId(), orderDto)).withRel("create"));
    }
}
