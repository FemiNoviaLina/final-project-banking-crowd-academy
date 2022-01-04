package com.fnvls.userservice.api.service;


import com.fnvls.userservice.api.dto.output.InterestOutputDto;

import java.util.List;

public interface InterestService {
    InterestOutputDto createInterest(Long id, List<String> categoryId);

    InterestOutputDto getInterest(Long id);
}
