package com.epam.esm.controller;

import com.epam.esm.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.hateoas.assemblers.UserModelAssembler;
import com.epam.esm.response.ResponseHandler;
import com.epam.esm.response.ResponseMessage;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.epam.esm.util.EndpointName.*;
import static com.epam.esm.util.QueryParam.*;


@RestController
@RequestMapping(path = BASE_URL, produces = JSON)
@CrossOrigin(origins = LOCALHOST)
public class UserController {

    private final UserService userService;
    private final DtoConverter<User, UserDto> dtoConverter;
    private final HateoasAdder<UserDto> hateoasAdder;
    private final UserModelAssembler userModelAssembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    @Autowired
    public UserController(UserService userService, DtoConverter<User, UserDto> dtoConverter, HateoasAdder<UserDto> hateoasAdder, UserModelAssembler userModelAssembler, PagedResourcesAssembler<User> pagedResourcesAssembler) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
        this.hateoasAdder = hateoasAdder;
        this.userModelAssembler = userModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @RequestMapping(value = USERS, params = REQUEST_ID, method = RequestMethod.GET)
    public ResponseEntity<Object> findUserById(@RequestParam Long id) {
        //Retrieve User from the DB
        Optional<User> userOptional = userService.findById(id);
        User user;
        //Extract User from Mapper
        if (userOptional.isPresent()) {
            user = userOptional.get();
            //Convert User to DTO for HATEOAS
            UserDto userDto = dtoConverter.convertToDto(user);
            hateoasAdder.addLinks(userDto);
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, userDto);
        } else {
            return ResponseHandler.generateResponse("User with id ( " + id + " ) was not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(USERS)
    public PagedModel<UserDto> findAllUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                              @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        Page<User> giftPage = userService.findAll(page, size);
        return pagedResourcesAssembler.toModel(giftPage, userModelAssembler);
    }
}
