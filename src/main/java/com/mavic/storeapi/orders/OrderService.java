package com.mavic.storeapi.orders;

import com.mavic.storeapi.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private AuthService authService;
    private OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return orders.stream().map(orderMapper::toOrderDto).toList();
    }

    public OrderDto getOrderById(Long orderId){
        var order = orderRepository.getOrderById(orderId).orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user)){
            throw new AccessDeniedException("You do not have access to this order");
        }
        return orderMapper.toOrderDto(order);
    }
}
