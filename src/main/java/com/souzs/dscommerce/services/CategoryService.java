package com.souzs.dscommerce.services;

import com.souzs.dscommerce.dto.CategoryDTO;
import com.souzs.dscommerce.entities.Category;
import com.souzs.dscommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> new CategoryDTO(category)).toList();
    }
}
