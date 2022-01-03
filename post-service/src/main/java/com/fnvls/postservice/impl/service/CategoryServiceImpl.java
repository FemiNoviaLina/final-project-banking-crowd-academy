package com.fnvls.postservice.impl.service;

import com.fnvls.postservice.api.dto.input.CategoryInputDto;
import com.fnvls.postservice.api.dto.output.CategoryOutputDto;
import com.fnvls.postservice.api.service.CategoryService;
import com.fnvls.postservice.data.Category;
import com.fnvls.postservice.impl.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryOutputDto createCategory(CategoryInputDto input) {
        Category category = Category.builder().name(input.getName()).build();
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        category = categoryRepository.save(category);
        CategoryOutputDto out = modelMapper.map(category, CategoryOutputDto.class);
        return out;
    }

    @Override
    public List<CategoryOutputDto> getAllCategory(Integer limit, Integer offset) {
        Pageable paging = PageRequest.of(offset, limit);
        Page<Category> categoryPage = categoryRepository.findAll(paging);

        List<Category> categories = categoryPage.getContent();
        List<CategoryOutputDto> out = new ArrayList<>();
        for (Category category : categories) {
            out.add(modelMapper.map(category, CategoryOutputDto.class));
        }
        return out;
    }

    @Override
    public CategoryOutputDto getCategory(String id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty()) return null;

        CategoryOutputDto out = modelMapper.map(category.get(), CategoryOutputDto.class);
        return out;
    }

    @Override
    public CategoryOutputDto updateCategory(String id, CategoryInputDto input) {
        Optional<Category> tempCategory = categoryRepository.findById(id);

        if(tempCategory.isEmpty()) return null;

        Category category = tempCategory.get();
        modelMapper.map(input, category);
        category.setUpdatedAt(new Date());
        category = categoryRepository.save(category);

        CategoryOutputDto out = modelMapper.map(category, CategoryOutputDto.class);
        return out;
    }

    @Override
    public void deleteCategory(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isEmpty()) categoryRepository.delete(category.get());
    }
}
