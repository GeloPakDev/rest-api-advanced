package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.impl.TagDtoConverter;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static com.epam.esm.util.QueryParam.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagHateoasAdder implements HateoasAdder<TagDto> {
    public static final Class<TagController> CONTROLLER_CLASS = TagController.class;
    private final TagDtoConverter converter = new TagDtoConverter();

    @Override
    public void addLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(CONTROLLER_CLASS).findAllTags(DEFAULT_MIN_PAGE, DEFAULT_MIN_SIZE)).withSelfRel());
        tagDto.add(linkTo(methodOn(CONTROLLER_CLASS).createTag(converter.convertToEntity(tagDto))).withRel(CREATE));
        tagDto.add(linkTo(methodOn(CONTROLLER_CLASS).findTheMostPopularTagsOfUsesOrders(DEFAULT_MIN_PAGE, DEFAULT_MIN_SIZE)).withRel(POPULAR));
        tagDto.add(linkTo(methodOn(CONTROLLER_CLASS).deleteTag(tagDto.getId())).withRel(DELETE));
        tagDto.add(linkTo(methodOn(CONTROLLER_CLASS).findTagById(tagDto.getId())).withRel(FIND_BY_ID));
        tagDto.add(linkTo(methodOn(CONTROLLER_CLASS).findByTagName(tagDto.getName())).withRel(FIND_BY_NAME));
    }
}