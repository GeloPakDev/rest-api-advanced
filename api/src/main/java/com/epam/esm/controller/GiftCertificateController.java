package com.epam.esm.controller;

import com.epam.esm.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.hateoas.assemblers.GiftModelAssembler;
import com.epam.esm.response.ResponseHandler;
import com.epam.esm.response.ResponseMessage;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.epam.esm.util.EndpointName.*;
import static com.epam.esm.util.QueryParam.*;

@RestController
@RequestMapping(path = BASE_URL, produces = JSON)
@CrossOrigin(origins = LOCALHOST)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final DtoConverter<GiftCertificate, GiftCertificateDto> dtoConverter;
    private final HateoasAdder<GiftCertificateDto> hateoasAdder;
    private final GiftModelAssembler giftModelAssembler;
    private final PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;


    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, DtoConverter<GiftCertificate, GiftCertificateDto> dtoConverter, HateoasAdder<GiftCertificateDto> hateoasAdder, GiftModelAssembler giftModelAssembler, PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler) {
        this.giftCertificateService = giftCertificateService;
        this.dtoConverter = dtoConverter;
        this.hateoasAdder = hateoasAdder;
        this.giftModelAssembler = giftModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    //GET mappings
    @RequestMapping(value = GIFT_CERTIFICATES, params = REQUEST_ID, method = RequestMethod.GET)
    public ResponseEntity<Object> findGiftById(@RequestParam Long id) {
        //Retrieve GiftCertificate from the DB
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateService.findById(id);
        GiftCertificate giftCertificate;
        //Extract GiftCertificate from Mapper
        if (giftCertificateOptional.isPresent()) {
            giftCertificate = giftCertificateOptional.get();
            //Convert Order to DTO for HATEOAS
            GiftCertificateDto giftDTO = dtoConverter.convertToDto(giftCertificate);
            hateoasAdder.addLinks(giftDTO);
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, giftDTO);
        } else {
            return ResponseHandler.generateResponse("Gift with id ( " + id + " ) was not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(GIFT_CERTIFICATES)
    public PagedModel<GiftCertificateDto> findAllGiftCertificates(@RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                                              @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<GiftCertificate> giftPage = giftCertificateService.findAll(page, size);
        return pagedResourcesAssembler.toModel(giftPage, giftModelAssembler);
    }

    @RequestMapping(GIFT_CERTIFICATES + FILTER)
    public PagedModel<GiftCertificateDto> findGiftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams,
                                                                      @RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                                                      @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<GiftCertificate> giftPage = giftCertificateService.doFilter(allRequestParams, page, size);
        return pagedResourcesAssembler.toModel(giftPage, giftModelAssembler);
    }

    //POST Mappings
    @PostMapping(value = GIFT_CERTIFICATES, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate gift = giftCertificateService.create(giftCertificate);
        GiftCertificateDto giftCertificateDto = dtoConverter.convertToDto(gift);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED, HttpStatus.OK, giftCertificateDto);
    }

    //UPDATE Mappings
    @PatchMapping(path = GIFT_CERTIFICATES + ID, consumes = JSON)
    public ResponseEntity<Object> updateGiftCertificate(@PathVariable(REQUEST_ID) Long id, @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate gift = giftCertificateService.update(id, giftCertificate);
        GiftCertificateDto giftCertificateDto = dtoConverter.convertToDto(gift);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_UPDATED + id, HttpStatus.OK, giftCertificateDto);
    }

    //DELETE Mappings
    @RequestMapping(value = GIFT_CERTIFICATES, params = REQUEST_ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteGiftCertificate(@RequestParam Long id) {
        GiftCertificate gift = giftCertificateService.delete(id);
        GiftCertificateDto giftCertificateDto = dtoConverter.convertToDto(gift);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED + id, HttpStatus.OK, giftCertificateDto);
    }
}