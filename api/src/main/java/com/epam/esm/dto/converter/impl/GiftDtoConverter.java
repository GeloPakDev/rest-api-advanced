package com.epam.esm.dto.converter.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class GiftDtoConverter implements DtoConverter<GiftCertificate, GiftCertificateDto> {
    @Override
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(dto.getId());
        giftCertificate.setName(dto.getName());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setCreateDate(dto.getCreateDate());
        giftCertificate.setLastUpdateDate(dto.getLastUpdateDate());
        giftCertificate.setTags(dto.getTags());
        return giftCertificate;
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate entity) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();

        giftCertificateDto.setId(entity.getId());
        giftCertificateDto.setName(entity.getName());
        giftCertificateDto.setDescription(entity.getDescription());
        giftCertificateDto.setPrice(entity.getPrice());
        giftCertificateDto.setDuration(entity.getDuration());
        giftCertificateDto.setCreateDate(entity.getCreateDate());
        giftCertificateDto.setLastUpdateDate(entity.getLastUpdateDate());
        giftCertificateDto.setTags(entity.getTags());

        return giftCertificateDto;
    }
}
