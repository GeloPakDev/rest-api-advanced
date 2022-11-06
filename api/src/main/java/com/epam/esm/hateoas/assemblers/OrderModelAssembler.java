package com.epam.esm.hateoas.assemblers;

import com.epam.esm.Order;
import com.epam.esm.hateoas.assemblers.domain.OrderModel;
import com.epam.esm.controller.OrderController;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {
    public OrderModelAssembler() {
        super(OrderController.class, OrderModel.class);
    }

    @Override
    public OrderModel toModel(Order entity) {
        OrderModel model = new OrderModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
