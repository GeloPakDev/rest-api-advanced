package com.epam.esm.controller;

import com.epam.esm.Tag;
import com.epam.esm.config.CORSConfig;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.hateoas.assemblers.TagModelAssembler;
import com.epam.esm.response.ResponseHandler;
import com.epam.esm.response.ResponseMessage;
import com.epam.esm.service.TagService;
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

/**
 * Class {@code TagController} is an endpoint of the API
 * which allows to perform CRD operations on Order with support of Filtering and Pagination
 * <p>
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/api/tags".
 * So that {@code TagController} is accessed by sending request to /api/tags.
 *
 * @author Oleg Pak
 * @since 1.0
 */
@RestController
@RequestMapping(path = BASE_URL + TAGS, produces = JSON)
@CrossOrigin(origins =  CORSConfig.LOCALHOST)
public class TagController {

    private final TagService tagService;
    private final DtoConverter<Tag, TagDto> dtoConverter;
    private final HateoasAdder<TagDto> hateoasAdder;
    private final TagModelAssembler tagModelAssembler;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;

    @Autowired
    public TagController(TagService tagService, DtoConverter<Tag, TagDto> dtoConverter, HateoasAdder<TagDto> hateoasAdder, TagModelAssembler tagModelAssembler, PagedResourcesAssembler<Tag> pagedResourcesAssembler) {
        this.tagService = tagService;
        this.dtoConverter = dtoConverter;
        this.hateoasAdder = hateoasAdder;
        this.tagModelAssembler = tagModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    //GET mappings

    /**
     * Method for fetching Tag by ID.
     *
     * @param id ID of Tag
     * @return Found Tag entity by ID with related action links supported by HATEOAS
     */
    @RequestMapping(params = REQUEST_ID)
    public ResponseEntity<Object> findTagById(@RequestParam Long id) {
        //Retrieve Tag from the DB
        Optional<Tag> optTag = tagService.findById(id);
        Tag tag;
        //Extract Tag from Mapper
        if (optTag.isPresent()) {
            tag = optTag.get();
            //Convert Tag to DTO for HATEOAS
            TagDto tagDto = dtoConverter.convertToDto(tag);
            hateoasAdder.addLinks(tagDto);
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, tagDto);
        } else {
            return ResponseHandler.generateResponse("Tag with id (" + id + ") was not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method for fetching Tag by Name.
     *
     * @param tagName Name of Tag
     * @return Found Tag entity by Name with related action links supported by HATEOAS
     */
    @RequestMapping(params = TAG_NAME)
    public ResponseEntity<Object> findByTagName(@RequestParam String tagName) {
        //Retrieve Tag from the DB
        Optional<Tag> optTag = tagService.findByName(tagName);
        Tag tag;
        //Extract Tag from Mapper
        if (optTag.isPresent()) {
            tag = optTag.get();
            //Convert Tag to DTO for HATEOAS
            TagDto tagDto = dtoConverter.convertToDto(tag);
            hateoasAdder.addLinks(tagDto);
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, tagDto);
        } else {
            return ResponseHandler.generateResponse("Tag with name (" + tagName + ") was not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method for fetching all Tag entities accessible in application
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return Found all Tag entities in the application with HATEOAS pagination support
     */
    @GetMapping
    public PagedModel<TagDto> findAllTags(@RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                          @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<Tag> tagPage = tagService.findAll(page, size);
        return pagedResourcesAssembler.toModel(tagPage, tagModelAssembler);
    }

    /**
     * Method for fetching the most popular Tags in the application
     * Annotated by {@link GetMapping} with parameter value = "/popular".
     * Accessed by sending request to /api/tags/popular.
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return Found all Tag entities in the application with HATEOAS pagination support
     */
    @GetMapping(POPULAR_TAG)
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<TagDto> findTheMostPopularTagsOfUsesOrders(@RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                                                 @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<Tag> tagPage = tagService.findTheMostPopularTagsOfUsesOrders(page, size);
        return pagedResourcesAssembler.toModel(tagPage, tagModelAssembler);
    }

    //POST mappings
    /**
     * Method for creating Tag
     *
     * @param tag Tag to create
     * @return Created Tag with related action links supported by HATEOAS
     */
    @PostMapping(consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.create(tag);
        TagDto tagDto = dtoConverter.convertToDto(createdTag);
        hateoasAdder.addLinks(tagDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED, HttpStatus.OK, tagDto);
    }

    //DELETE mappings
    /**
     * Method for deleting Tag
     *
     * @param id ID of Tag to delete
     * @return Deleted Tag with related action links supported by HATEOAS
     */
    @RequestMapping(params = REQUEST_ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteTag(@RequestParam Long id) {
        Tag createdTag = tagService.delete(id);
        TagDto tagDto = dtoConverter.convertToDto(createdTag);
        hateoasAdder.addLinks(tagDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED_TAG + id, HttpStatus.OK, tagDto);
    }
}