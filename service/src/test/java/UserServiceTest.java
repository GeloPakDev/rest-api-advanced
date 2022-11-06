import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.impl.UserDaoImpl;
import com.epam.esm.service.impl.UserServiceImpl;
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
public class UserServiceTest {
    @Mock
    private UserDaoImpl userDao = Mockito.mock(UserDaoImpl.class);

    @InjectMocks
    private UserServiceImpl userService;
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2018-08-29T06:12:15.156");
    private static final Order ORDER_1 = new Order(1L, 152, 1L, UPDATED_DATE,
            Stream.of(new GiftCertificate(1L, "giftCertificate1", "description1", 10.1,
                    1, LocalDateTime.parse("2020-08-29T06:12:15"), LocalDateTime.parse("2020-08-29T06:12:15"), null)).collect(Collectors.toSet()));
    private static final Order ORDER_2 = new Order(2L, 304, 1L, UPDATED_DATE,
            Stream.of(new GiftCertificate(2L, "giftCertificate2", "description2", 30.1,
                    3, LocalDateTime.parse("2019-08-29T06:12:15"), LocalDateTime.parse("2019-08-29T06:12:15"), null)).collect(Collectors.toSet()));
    private static final Order ORDER_3 = new Order(3L, 304, 2L, UPDATED_DATE,
            Stream.of(new GiftCertificate(3L, "giftCertificate3", "description3", 30.1,
                    3, LocalDateTime.parse("2019-08-29T06:12:15"), LocalDateTime.parse("2019-08-29T06:12:15"), null)).collect(Collectors.toSet()));
    private static final User USER_1 = new User(1L, "name1", Stream.of(ORDER_1).collect(Collectors.toSet()));
    private static final User USER_2 = new User(2L, "name2", Stream.of(ORDER_2).collect(Collectors.toSet()));
    private static final User USER_3 = new User(3L, "name3", Stream.of(ORDER_3).collect(Collectors.toSet()));

    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    void testFindById() {
        when(userDao.findById(ORDER_1.getId())).thenReturn(Optional.of(USER_1));

        Optional<User> actualWrapper = userService.findById(USER_1.getId());
        User user = new User();
        if (actualWrapper.isPresent()) {
            user = actualWrapper.get();
        }

        assertEquals(USER_1, user);
    }

    @Test
    void testFindAll() {
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        List<User> userList = Arrays.asList(USER_1, USER_2 , USER_3);
        Page<User> users = new PageImpl<>(userList, pageRequest, userList.size());
        when(userDao.findAll(pageRequest)).thenReturn(users);
        Page<User> actual = userService.findAll(PAGE, SIZE);
        assertEquals(actual, users);
    }
}