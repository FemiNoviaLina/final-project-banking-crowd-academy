package com.fnvls.postservice.api.service;

import com.fnvls.postservice.api.dto.input.CategoryInputDto;
import com.fnvls.postservice.api.dto.output.CategoryOutputDto;

import java.util.List;

public interface CategoryService {
    CategoryOutputDto createCategory(CategoryInputDto input);
    List<CategoryOutputDto> getAllCategory(Integer limit, Integer offset);
    CategoryOutputDto getCategory(String id);
    CategoryOutputDto updateCategory(String id, CategoryInputDto input);
    void deleteCategory(String id);
}
