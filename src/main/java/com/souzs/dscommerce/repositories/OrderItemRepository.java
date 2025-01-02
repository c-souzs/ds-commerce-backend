package com.souzs.dscommerce.repositories;

import com.souzs.dscommerce.entities.OrderItem;
import com.souzs.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
