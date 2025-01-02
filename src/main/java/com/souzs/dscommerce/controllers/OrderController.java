package com.souzs.dscommerce.controllers;

import com.souzs.dscommerce.dto.OrderDTO;
import com.souzs.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getById(
            @PathVariable Long id
    ) {
        OrderDTO result = orderService.findById(id);

        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> post(
            @Valid @RequestBody OrderDTO dto
    ) {
        dto = orderService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }
}
