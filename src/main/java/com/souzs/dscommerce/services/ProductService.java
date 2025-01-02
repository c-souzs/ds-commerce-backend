package com.souzs.dscommerce.services;

import com.souzs.dscommerce.dto.ProductDTO;
import com.souzs.dscommerce.dto.ProductMinDTO;
import com.souzs.dscommerce.entities.Category;
import com.souzs.dscommerce.entities.Product;
import com.souzs.dscommerce.repositories.ProductRepository;
import com.souzs.dscommerce.services.exceptions.DatabaseException;
import com.souzs.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado.")
        );
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
        return repository.searchByName(name, pageable).map(ProductMinDTO::new);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        copyDtoToEntity(dto, product);

        product = repository.save(product);

        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            // Prepara um objeto monitorado pela JPA, com o id do produto a ser atualizado
            // porem ele nao busca no banco de dados
            Product product = repository.getReferenceById(id);
            copyDtoToEntity(dto, product);

            product = repository.save(product);

            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado.");
        }
    }

    // So executa a transacao se o metodo estiver no contexto
    // de outra transacao.
    // Caso nao esteja, ele nao utiliza a transacao.
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)) throw new ResourceNotFoundException("Recurso não encontrado");

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());
        product.getCategories().clear();

        dto.getCategories().forEach(category -> {
            Category categoryAssociation = new Category();
            categoryAssociation.setId(category.getId());

            product.getCategories().add(categoryAssociation);
        });
    }

}
