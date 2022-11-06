import com.epam.esm.Tag;
import com.epam.esm.impl.TagDaoImpl;
import com.epam.esm.service.impl.TagServiceImpl;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;

    //TAGS
    private static final Tag TAG_1 = new Tag(1L, "tagName1");
    private static final Tag TAG_2 = new Tag(2L, "tagName3");
    private static final Tag TAG_3 = new Tag(3L, "tagName5");
    private static final Tag TAG_4 = new Tag(4L, "tagName4");
    private static final Tag TAG_5 = new Tag(5L, "tagName2");
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    public void testGetById() {
        when(tagDao.findById(TAG_3.getId())).thenReturn(Optional.of(TAG_3));

        Optional<Tag> actual = tagService.findById(TAG_3.getId());
        Tag tag = new Tag();
        if (actual.isPresent()) {
            tag = actual.get();
        }
        assertEquals(TAG_3, tag);
    }

    @Test
    void testFindTagByName() {
        when(tagDao.findByName(TAG_1.getName())).thenReturn(Optional.of(TAG_1));
        Optional<Tag> actual = tagService.findByName(TAG_1.getName());
        Tag tag = new Tag();
        if (actual.isPresent()) {
            tag = actual.get();
        }
        assertEquals(TAG_1, tag);
    }

    @Test
    void testFindAllTags() {
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        List<Tag> orderList = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        Page<Tag> orders = new PageImpl<>(orderList, pageRequest, orderList.size());
        when(tagDao.findAll(pageRequest)).thenReturn(orders);
        Page<Tag> actual = tagService.findAll(PAGE, SIZE);
        assertEquals(actual, orders);
    }

    @Test
    void testFindPopularsTag() {
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        List<Tag> list = Arrays.asList(TAG_1, TAG_2, TAG_3);
        Page<Tag> tags = new PageImpl<>(list, pageRequest, list.size());
        when(tagDao.findTheMostPopularTagsOfUsesOrders(pageRequest)).thenReturn(tags);

        Page<Tag> actual = tagService.findTheMostPopularTagsOfUsesOrders(PAGE, SIZE);

        assertEquals(tags, actual);
    }

    @Test
    void testCreateTag() {
        when(tagDao.create(TAG_1)).thenReturn(TAG_1);

        Tag actual = tagService.create(TAG_1);

        assertEquals(TAG_1, actual);
    }

    @Test
    void testDeleteTag() {
        when(tagDao.findById(1L)).thenReturn(Optional.of(TAG_1));
        when(tagDao.deleteById(1L)).thenReturn(TAG_1);

        Tag actual = tagService.delete(1L);

        assertEquals(TAG_1, actual);
    }

}