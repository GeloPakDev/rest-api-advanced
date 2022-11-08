package com.epam.esm.controller;

import com.epam.esm.Order;
import com.epam.esm.config.CORSConfig;
import com.epam.esm.hateoas.assemblers.OrderModelAssembler;
import com.epam.esm.hateoas.assemblers.domain.OrderModel;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.response.ResponseHandler;
import com.epam.esm.response.ResponseMessage;
import com.epam.esm.service.OrderService;
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
 * Class {@code OrderController} is an endpoint of the API
 * which allows to perform CREATE and READ operations on Order with support of Filtering and Pagination
 * <p>
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/api".
 * So that {@code OrderController} is accessed by sending request to /api/orders.
 *
 * @author Oleg Pak
 * @since 1.0
 */
@RestController
@RequestMapping(path = BASE_URL, produces = JSON)
@CrossOrigin(origins =  CORSConfig.LOCALHOST)
public class OrderController {

    private final OrderService orderService;
    private final DtoConverter<Order, OrderDto> dtoConverter;
    private final HateoasAdder<OrderDto> hateoasAdder;
    private final OrderModelAssembler orderModelAssembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;


    public OrderController(OrderService orderService, DtoConverter<Order, OrderDto> dtoConverter, HateoasAdder<OrderDto> hateoasAdder, OrderModelAssembler orderModelAssembler, PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        this.orderService = orderService;
        this.dtoConverter = dtoConverter;
        this.hateoasAdder = hateoasAdder;
        this.orderModelAssembler = orderModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    //GET mappings

    /**
     * Method for fetching Order by ID.
     *
     * @param id ID of Order
     * @return Found Order entity by ID with related action links supported by HATEOAS
     */
    @RequestMapping(value = ORDERS, params = REQUEST_ID, method = RequestMethod.GET)
    public ResponseEntity<Object> findOrderById(@RequestParam Long id) {
        //Retrieve Order from the DB
        Optional<Order> orderOptional = orderService.findOrderById(id);
        Order order;
        //Extract Order from Mapper
        if (orderOptional.isPresent()) {
            order = orderOptional.get();
            //Convert Order to DTO for HATEOAS
            OrderDto orderDto = dtoConverter.convertToDto(order);
            hateoasAdder.addLinks(orderDto);
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, orderDto);
        } else {
            return ResponseHandler.generateResponse("Order with id ( " + id + " ) was not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method for fetching all Order entities accessible in application
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return Found all Order entities in the application with HATEOAS pagination support
     */
    @RequestMapping(ORDERS)
    public PagedModel<OrderModel> findAllOrders(@RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                                @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<Order> orderPage = orderService.findAll(page, size);
        return pagedResourcesAssembler.toModel(orderPage, orderModelAssembler);
    }

    /**
     * Method for fetching all User's Order entities accessible in application
     *
     * @param id ID of the User
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return Found all User's Order entities in the application with HATEOAS pagination support
     */
    @GetMapping(path = USERS + ID + ORDERS)
    public PagedModel<OrderModel> findAllUserOrders(@PathVariable(REQUEST_ID) Long id,
                                                    @RequestParam(value = PAGE, defaultValue = "0", required = false) int page,
                                                    @RequestParam(value = SIZE, defaultValue = "5", required = false) int size) {
        Page<Order> orderPage = orderService.findUsersOrders(id, page, size);
        return pagedResourcesAssembler.toModel(orderPage, orderModelAssembler);
    }

    /**
     * Method for fetching particular Order of User by ID accessible in application
     *
     * @param userId ID of the User
     * @param orderId ID of the User's Order
     * @return Found particular Order of User by ID in the application with related action links supported by HATEOAS
     */
    @GetMapping(path = USERS + ID + ORDERS + USER_ORDER_ID)
    public ResponseEntity<Object> findUserOrderById(@PathVariable(REQUEST_ID) Long userId, @PathVariable(ORDER_ID) Long orderId) {
        //Retrieve Order from the DB
        Optional<Order> orderOptional = orderService.findUserOrderById(userId, orderId);
        Order order;
        //Extract Order from Mapper
        if (orderOptional.isPresent()) {
            order = orderOptional.get();
            //Convert Order to DTO for HATEOAS
            OrderDto orderDto = dtoConverter.convertToDto(order);
            hateoasAdder.addLinks(orderDto);
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, orderDto);
        } else {
            return ResponseHandler.generateResponse("Order with id (" + orderId + ") was not found of user with id (" + userId + ")", HttpStatus.NOT_FOUND);
        }
    }

    //POST mappings
    /**
     * Method for creating Order
     *
     * @param id ID of the User
     * @param orderDto Order to create
     * @return Created Order with related action links supported by HATEOAS
     */
    @PostMapping(path = USERS + ID + ORDERS, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createGiftCertificate(@PathVariable(REQUEST_ID) Long id, @RequestBody OrderDto orderDto) {
        Order order = orderService.createOrder(id, dtoConverter.convertToEntity(orderDto));
        OrderDto orderDTO = dtoConverter.convertToDto(order);
        hateoasAdder.addLinks(orderDTO);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED, HttpStatus.OK, orderDTO);
    }
}