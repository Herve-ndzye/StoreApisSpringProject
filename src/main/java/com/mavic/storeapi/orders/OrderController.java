package com.mavic.storeapi.orders;

import com.mavic.storeapi.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public  OrderDto getOrderById(@PathVariable("orderId") Long orderId){
        return orderService.getOrderById(orderId);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<ErrorDto> handleOrderNotFound(Exception ex){
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorDto> handleAccessDenied(Exception ex){
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}
