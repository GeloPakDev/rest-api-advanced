import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.impl.OrderDaoImpl;
import com.epam.esm.service.impl.OrderServiceImpl;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderDaoImpl orderDao = Mockito.mock(OrderDaoImpl.class);

    @InjectMocks
    private OrderServiceImpl orderService;
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2018-08-29T06:12:15.156");

    private static final Order ORDER_1 = new Order(1L, 152, 1L, UPDATED_DATE,
            Stream.of(new GiftCertificate(1L, "giftCertificate1", "description1", 10.1,
                    1, LocalDateTime.parse("2020-08-29T06:12:15"), LocalDateTime.parse("2020-08-29T06:12:15"), null)).collect(Collectors.toSet()));
    private static final Order ORDER_2 = new Order(2L, 304, 1L, UPDATED_DATE,
            Stream.of(new GiftCertificate(2L, "giftCertificate2", "description2", 30.1,
                    3, LocalDateTime.parse("2019-08-29T06:12:15"), LocalDateTime.parse("2019-08-29T06:12:15"), null)).collect(Collectors.toSet()));


    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    public void testGetById() {
        when(orderDao.findById(ORDER_1.getId())).thenReturn(Optional.of(ORDER_1));

        Optional<Order> actualWrapper = orderService.findOrderById(ORDER_1.getId());
        Order order = new Order();
        if (actualWrapper.isPresent()) {
            order = actualWrapper.get();
        }

        assertEquals(ORDER_1, order);
    }

    @Test
    public void testFindAll() {
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        List<Order> orderList = Arrays.asList(ORDER_1, ORDER_2);
        Page<Order> orders = new PageImpl<>(orderList, pageRequest, orderList.size());
        when(orderDao.findAll(pageRequest)).thenReturn(orders);
        Page<Order> actual = orderService.findAll(PAGE, SIZE);
        assertEquals(actual, orders);
    }

    @Test
    public void testFindUsersOrders() {
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        List<Order> orderList = Arrays.asList(ORDER_1, ORDER_2);
        Page<Order> orders = new PageImpl<>(orderList, pageRequest, orderList.size());
        when(orderDao.findUsersOrders(1L, pageRequest)).thenReturn(orders);
        Page<Order> actual = orderService.findUsersOrders(1L, PAGE, SIZE);
        assertEquals(actual, orders);
    }

    @Test
    public void testFindUserOrdersById() {
        when(orderDao.findUserOrderById(1L, 1L)).thenReturn(Optional.of(ORDER_1));
        Optional<Order> actual = orderService.findUserOrderById(1L, 1L);
        Order order = new Order();
        if (actual.isPresent()) {
            order = actual.get();
        }
        assertEquals(order, ORDER_1);
    }

}