package com.epam.esm.hateoas.assemblers.domain;

import com.epam.esm.GiftCertificate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderModel extends RepresentationModel<OrderModel> {
    private Long id;
    private int price;
    private Long userId;
    private LocalDateTime orderDate;
    private Set<GiftCertificate> gifts;
}