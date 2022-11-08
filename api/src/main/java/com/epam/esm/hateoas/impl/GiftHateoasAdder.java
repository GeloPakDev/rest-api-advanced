package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.impl.GiftDtoConverter;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static com.epam.esm.util.QueryParam.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftHateoasAdder implements HateoasAdder<GiftCertificateDto> {
    public static final Class<GiftCertificateController> CONTROLLER_CLASS = GiftCertificateController.class;

    private final GiftDtoConverter converter = new GiftDtoConverter();

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER_CLASS).findAllGiftCertificates(DEFAULT_MIN_PAGE, DEFAULT_MIN_SIZE)).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER_CLASS).createGiftCertificate(converter.convertToEntity(giftCertificateDto))).withRel(CREATE));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER_CLASS).deleteGiftCertificate(giftCertificateDto.getId())).withRel(DELETE));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER_CLASS).findGiftById(giftCertificateDto.getId())).withRel(FIND_BY_ID));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER_CLASS).updateGiftCertificate(giftCertificateDto.getId(), converter.convertToEntity(giftCertificateDto))).withRel(UPDATE));
    }
}