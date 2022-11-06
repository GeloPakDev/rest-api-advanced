package com.epam.esm.hateoas.assemblers;

import com.epam.esm.User;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {
    public UserModelAssembler() {
        super(UserController.class, UserDto.class);
    }

    @Override
    public UserDto toModel(User entity) {
        UserDto model = new UserDto();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
