package com.fnvls.postservice.impl.service;

import com.fnvls.postservice.impl.repository.CategoryRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    private static EasyRandom EASY_RANDOM = new EasyRandom();
    private String RANDOM_ID;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository repository;

    @Spy
    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RANDOM_ID = Long.toString(EASY_RANDOM.nextLong());
    }

    @Test
    void createCategory() {
    }

    @Test
    void getCategoryWillReturnNull() {
        when(repository.findById(RANDOM_ID)).thenReturn(Optional.empty());

        var result = categoryService.getCategory(RANDOM_ID);

        assertNull(result);
        verify(repository, times(1)).findById(RANDOM_ID);
    }
}