package com.fnvls.postservice.impl.repository;

import com.fnvls.postservice.data.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, String> {
    Category findDistinctByName(String name);
}
