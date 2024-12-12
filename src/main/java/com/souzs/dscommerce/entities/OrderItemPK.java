package com.souzs.dscommerce.entities;

import jakarta.persistence.*;

// Como vai ser uma classe de associacao, que representa uma chave primaria composta,
// usamos o Embeddable, que incorpora seus atributos e metodos na classe que o usa.
// Logo, podemos pensar que estamos na classe OrderItem para facilitar o entendimento
@Embeddable
public class OrderItemPK {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItemPK() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
