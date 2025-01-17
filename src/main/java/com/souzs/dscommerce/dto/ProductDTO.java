package com.souzs.dscommerce.dto;

import com.souzs.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    private Long id;
    // Verifica se nao e nulo, nao e vazio e nao e apenas espacos
    @NotBlank(message = "Campo requerido.")
    @Size(min = 3, max = 80, message = "Nome deve ter entre 3 e 80 caracteres.")
    private String name;
    @NotBlank(message = "Campo requerido.")
    @Size(min = 10, message = "Descrição de ter no mínimo 10 caracteres.")
    private String description;
    @NotNull(message = "Campo requerido.")
    @Positive(message = "O preço deve ser positivo.")
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "Deve ter pelo menos uma categoria.")
    List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        imgUrl = product.getImgUrl();

        product.getCategories().forEach(category -> categories.add(new CategoryDTO(category)));
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
