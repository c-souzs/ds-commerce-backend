package com.souzs.dscommerce.services;

import com.souzs.dscommerce.dto.OrderDTO;
import com.souzs.dscommerce.entities.*;
import com.souzs.dscommerce.repositories.OrderItemRepository;
import com.souzs.dscommerce.repositories.OrderRepository;
import com.souzs.dscommerce.repositories.ProductRepository;
import com.souzs.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado.")
        );

        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        dto.getItems().forEach(item -> {
            Product product =  productRepository.getReferenceById(item.getProductId());

            OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), product.getPrice());

            order.getItems().add(orderItem);
        });

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
