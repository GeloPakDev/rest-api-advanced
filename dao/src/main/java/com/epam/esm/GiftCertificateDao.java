package com.epam.esm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GiftCertificateDao extends EntityDao<GiftCertificate, Long> {
    Page<GiftCertificate> findWithFilters(MultiValueMap<String, String> fields, Pageable pageable);

    GiftCertificate update(GiftCertificate giftCertificate);
}
