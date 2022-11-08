import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.impl.GiftCertificateDaoImpl;
import com.epam.esm.service.impl.GiftCertificateImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @InjectMocks
    private GiftCertificateImpl giftCertificateService;

    private static final Tag TAG_2 = new Tag(2L, "tagNameThree");

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1L, "giftCertificateOne",
            "descriptionOne", 10.1, 1, LocalDateTime.parse("2020-08-29T06:12:15.156"),
            LocalDateTime.parse("2020-08-29T06:12:15.156"), Stream.of(new Tag(1L, "tagNameOne"),
            new Tag(2L, "tagNameThree"), new Tag(3L, "tagNameFive")).collect(Collectors.toSet()));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2L, "giftCertificateTwo",
            "descriptionTwo", 30.3, 3, LocalDateTime.parse("2019-08-29T06:12:15.156"),
            LocalDateTime.parse("2019-08-29T06:12:15.156"), Stream.of(new Tag(2L, "tagNameThree")).collect(Collectors.toSet()));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3L, "giftCertificateThree",
            "descriptionThree", 20.2, 2, LocalDateTime.parse("2018-08-29T06:12:15.156"),
            LocalDateTime.parse("2018-08-29T06:12:15.156"), null);

    private static final String SORT_PARAMETER = "DESC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    void testGetById() {
        when(giftCertificateDao.findById(GIFT_CERTIFICATE_2.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        Optional<GiftCertificate> optionalGift = giftCertificateService.findById(GIFT_CERTIFICATE_2.getId());
        GiftCertificate actual = new GiftCertificate();
        if (optionalGift.isPresent()) {
            actual = optionalGift.get();
        }

        assertEquals(GIFT_CERTIFICATE_2, actual);
    }

    @Test
    void testFindAll() {
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        List<GiftCertificate> list = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Page<GiftCertificate> giftCertificates = new PageImpl<>(list, pageRequest, list.size());
        when(giftCertificateDao.findAll(pageRequest)).thenReturn(giftCertificates);
        Page<GiftCertificate> actual = giftCertificateService.findAll(PAGE, SIZE);

        assertEquals(actual, giftCertificates);
    }

    @Test
    void testDoFilter() {
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        List<GiftCertificate> list = Arrays.asList(GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1);
        Page<GiftCertificate> giftCertificates = new PageImpl<>(list, pageRequest, list.size());

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("tag_name", TAG_2.getName());
        requestParams.add("sortByName", SORT_PARAMETER);

        when(giftCertificateDao.findWithFilters(requestParams, pageRequest)).thenReturn(giftCertificates);

        Page<GiftCertificate> actual = giftCertificateService.doFilter(requestParams, PAGE, SIZE);

        assertEquals(giftCertificates, actual);
    }

    @Test
    void testCreate() {
        when(giftCertificateDao.create(GIFT_CERTIFICATE_3)).thenReturn(GIFT_CERTIFICATE_3);

        GiftCertificate actual = giftCertificateService.create(GIFT_CERTIFICATE_3);

        assertEquals(GIFT_CERTIFICATE_3, actual);
    }

    @Test
    void testUpdate() {
        when(giftCertificateDao.findById(GIFT_CERTIFICATE_3.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_3));
        when(giftCertificateDao.update(GIFT_CERTIFICATE_3)).thenReturn(GIFT_CERTIFICATE_3);

        GiftCertificate actual = giftCertificateService.update(GIFT_CERTIFICATE_3.getId(), GIFT_CERTIFICATE_3);
        assertEquals(GIFT_CERTIFICATE_3, actual);
    }
}
