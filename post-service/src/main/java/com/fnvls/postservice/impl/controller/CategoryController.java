package com.fnvls.postservice.impl.controller;

import com.fnvls.postservice.api.dto.input.CategoryInputDto;
import com.fnvls.postservice.api.dto.output.CategoryOutputDto;
import com.fnvls.postservice.api.response.BaseResponse;
import com.fnvls.postservice.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<BaseResponse<CategoryOutputDto>>> getAllCategory(
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        List<CategoryOutputDto> categories = categoryService.getAllCategory(limit, offset);

        return new ResponseEntity(new BaseResponse<>(categories), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<BaseResponse<CategoryOutputDto>> getCategory (@PathVariable String id) {
        CategoryOutputDto category = categoryService.getCategory(id);

        if(category == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Category not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(new BaseResponse<>(category), HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<BaseResponse<CategoryOutputDto>> createCategory(@RequestBody CategoryInputDto input) {
        CategoryOutputDto category = categoryService.createCategory(input);

        return new ResponseEntity<>(new BaseResponse<>(category), HttpStatus.CREATED);
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<BaseResponse<CategoryOutputDto>> updateCategory(
            @PathVariable String id,
            @RequestBody CategoryInputDto input
    ) {
        CategoryOutputDto category = categoryService.updateCategory(id, input);

        if(category == null) return new ResponseEntity(new BaseResponse<>(Boolean.FALSE, "Category not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(new BaseResponse<>(category), HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity(new BaseResponse<>(Boolean.TRUE, "category deleted"), HttpStatus.OK);
    }
}