package com.epam.esm.hateoas.assemblers;

import com.epam.esm.GiftCertificate;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GiftModelAssembler extends RepresentationModelAssemblerSupport<GiftCertificate, GiftCertificateDto> {
    public GiftModelAssembler() {
        super(GiftCertificateController.class, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto toModel(GiftCertificate entity) {
        GiftCertificateDto model = new GiftCertificateDto();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
