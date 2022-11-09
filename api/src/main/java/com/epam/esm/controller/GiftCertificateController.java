package com.epam.esm.controller;

import com.epam.esm.GiftCertificate;
import com.epam.esm.config.CORSConfig;
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

/**
 * Class {@code GiftCertificateController} is an endpoint of the API
 * which allows to perform CRUD operations on Gift Certificates with support of Filtering and Pagination
 * <p>
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/api".
 * So that {@code GiftCertificateController} is accessed by sending request to /api/certificates.
 *
 * @author Oleg Pak
 * @since 1.0
 */
@RestController
@RequestMapping(path = BASE_URL, produces = JSON)
@CrossOrigin(origins = CORSConfig.LOCALHOST)
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

    /**
     * Method for fetching GiftCertificate by ID.
     *
     * @param id ID of GiftCertificate
     * @return Found GiftCertificate entity by ID with related action links supported by HATEOAS
     */
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

    /**
     * Method for fetching all GiftCertificate entities accessible in application
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return Found all GiftCertificate entities in the application with HATEOAS pagination support
     */
    @RequestMapping(GIFT_CERTIFICATES)
    public PagedModel<GiftCertificateDto> findAllGiftCertificates(@RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                                                  @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<GiftCertificate> giftPage = giftCertificateService.findAll(page, size);
        return pagedResourcesAssembler.toModel(giftPage, giftModelAssembler);
    }

    /**
     * Method for fetching all GiftCertificate accessible by applying various filters
     *
     * @param page             the number of page for pagination
     * @param size             the size of page for pagination
     * @param allRequestParams request parameters, which contain filters to be applied for search
     * @return Found all GiftCertificate entities with applied filters in the application with HATEOAS pagination support
     */
    @RequestMapping(GIFT_CERTIFICATES + FILTER)
    public PagedModel<GiftCertificateDto> findGiftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams,
                                                                          @RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                                                          @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<GiftCertificate> giftPage = giftCertificateService.doFilter(allRequestParams, page, size);
        return pagedResourcesAssembler.toModel(giftPage, giftModelAssembler);
    }

    //POST Mappings

    /**
     * Method for creating GiftCertificate
     *
     * @param giftCertificate GiftCertificate to create
     * @return Created GiftCertificate with related action links supported by HATEOAS
     */
    @PostMapping(value = GIFT_CERTIFICATES, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate gift = giftCertificateService.create(giftCertificate);
        GiftCertificateDto giftCertificateDto = dtoConverter.convertToDto(gift);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED, HttpStatus.OK, giftCertificateDto);
    }

    //UPDATE Mappings

    /**
     * Method for creating GiftCertificate
     *
     * @param id              ID of the GiftCertificate to update
     * @param giftCertificate GiftCertificate object which contains fields to update
     * @return Updated GiftCertificate with related action links supported by HATEOAS
     */
    @RequestMapping(path = GIFT_CERTIFICATES, params = REQUEST_ID, consumes = JSON, method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateGiftCertificate(@RequestParam Long id, @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate gift = giftCertificateService.update(id, giftCertificate);
        GiftCertificateDto giftCertificateDto = dtoConverter.convertToDto(gift);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_UPDATED + id, HttpStatus.OK, giftCertificateDto);
    }

    //DELETE Mappings

    /**
     * Method for deleting GiftCertificate
     *
     * @param id ID of GiftCertificate to delete
     * @return Deleted GiftCertificate with related action links supported by HATEOAS
     */
    @RequestMapping(value = GIFT_CERTIFICATES, params = REQUEST_ID, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteGiftCertificate(@RequestParam Long id) {
        GiftCertificate gift = giftCertificateService.delete(id);
        GiftCertificateDto giftCertificateDto = dtoConverter.convertToDto(gift);
        hateoasAdder.addLinks(giftCertificateDto);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED + id, HttpStatus.OK, giftCertificateDto);
    }
}