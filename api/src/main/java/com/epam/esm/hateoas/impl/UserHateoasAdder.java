package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static com.epam.esm.util.QueryParam.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoasAdder implements HateoasAdder<UserDto> {
    public static final Class<UserController> CONTROLLER_CLASS = UserController.class;

    @Override
    public void addLinks(UserDto userDto) {
        userDto.add(linkTo(methodOn(CONTROLLER_CLASS).findAllUsers(DEFAULT_MIN_PAGE, DEFAULT_MIN_SIZE)).withSelfRel());
        userDto.add(linkTo(methodOn(CONTROLLER_CLASS).findUserById(userDto.getId())).withRel(FIND_BY_ID));
    }
}