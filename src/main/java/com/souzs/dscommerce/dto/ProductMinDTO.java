package com.souzs.dscommerce.dto;

import com.souzs.dscommerce.entities.Product;

public class ProductMinDTO {
    private Long id;
    private String imgUrl;
    private String name;
    private String description;

    public ProductMinDTO(Long id, String imgUrl, String name, String description) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
    }

    public ProductMinDTO(Product entity) {
        id = entity.getId();
        imgUrl = entity.getImgUrl();
        name = entity.getName();
        description = entity.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
