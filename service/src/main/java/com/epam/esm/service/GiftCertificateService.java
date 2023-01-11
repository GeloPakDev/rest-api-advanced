package com.epam.esm.service;

import com.epam.esm.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Service
public interface GiftCertificateService {

    Optional<GiftCertificate> findById(Long id);

    Page<GiftCertificate> findAll(int page, int size);

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate delete(Long id);

    GiftCertificate update(Long id, GiftCertificate giftCertificate);

    Page<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams, int page, int size);
}